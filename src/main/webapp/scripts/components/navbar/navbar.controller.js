'use strict';

angular.module('stepApp')
    .controller('NavbarController', function ($scope, $rootScope, $location, $state, Auth, Principal, ENV, Cat, InstituteByLogin, CurrentInstEmployee, NotificationStep, ParseLinks) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';
        $scope.showMpo = true;
        $scope.showMpoLink = true;

        if(Principal.hasAnyAuthority(['ROLE_ADMIN']) || Principal.hasAnyAuthority(['ROLE_INSTITUTE'])) {
            InstituteByLogin.query({},function(result){
                if(!result.mpoEnlisted){
                    $rootScope.showMpoLink = false;
                }
            });

        }else if(Principal.hasAnyAuthority(['ROLE_ADMIN']) || Principal.hasAnyAuthority(['ROLE_INSTEMP'])) {
            CurrentInstEmployee.get({},function(result){
                if(!result.institute.mpoEnlisted){
                    $rootScope.showMpoLink = false;
                }

            });

        }
        /*
        Notification
         */
        $scope.notificationSteps = [];
        $scope.page = 0;
        $scope.notificationStepsSize = 0;
        $scope.notificationStep = [];

        //console.log('User ID, Notification'+$rootScope.accountId);

        Principal.identity().then(function (account) {
            if (account) {
                $scope.account = account;
                //ROLE_INSTEMP,ROLE_INSTITUTE

                NotificationStep.query({page: $scope.page, size: 20}, function(result, headers) {
                    $scope.links = ParseLinks.parse(headers('link'));
                    $scope.notificationSteps = result;
                    $scope.notificationStepsSize = $scope.notificationSteps.length;
                    //$rootScope.notifications = result;
                    //console.log($scope.notificationSteps);
                });


                if ($scope.isInArray('ROLE_INSTITUTE', $scope.account.authorities)) {
                    InstituteByLogin.query({}, function (result) {
                        if (result.type === 'Government') {
                            $scope.showMpo = false;
                            //console.log('fsgsfdg :'+$scope.showMpo);
                            $scope.userRole = 'ROLE_INSTITUTE';
                        }
                    });
                    $scope.userRole = 'ROLE_ADMIN';
                } else if ($scope.isInArray('ROLE_INSTEMP', $scope.account.authorities)) {
                    CurrentInstEmployee.get({}, function (result) {
                        if (result.institute.type === 'Government') {
                            $scope.showMpo = false;
                            //console.log('fsgsfdg :'+$scope.showMpo);
                        }
                    });
                    $scope.userRole = 'ROLE_ADMIN';
                }
            }

        });

        $scope.notificationClicked = function(id) {
            NotificationStep.get({id : id}, function(result) {
                $scope.notificationStep = result;

                $scope.notificationStep.status=false;
                console.log($scope.notificationStep);
                NotificationStep.update($scope.notificationStep, onSaveFinished);
                //if ($scope.notificationStep.id != null) {
                //
                //}

            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:notificationStepUpdate', result);
            NotificationStep.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.notificationSteps = result;
                $scope.notificationStepsSize = $scope.notificationSteps.length;
                //$rootScope.notifications = result;
                //console.log($scope.notificationSteps);
            });
            //console.log($scope.notificationStep);
            $state.go($scope.notificationStep.urls);
        };

        $scope.logout = function () {
            Auth.logout();
            $rootScope.accountName = 'Login';
            $state.go('home');
        };

        Cat.query({page: 0, size: -1}, function (result, headers) {
            $scope.cats = result;
        });

        $scope.submit = function () {
            var form = $scope.form;
            $scope.submitted = true;
            if (form.$valid) {
                var searchData = {'q': $scope.q};
                $state.go('search', searchData);
            }
        };

        $scope.isInArray = function isInArray(value, array) {
            return array.indexOf(value) > -1;
        };

    });
