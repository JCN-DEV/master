'use strict';

angular.module('stepApp').controller('EmployerChangePasswordController',
['Principal', '$scope', '$stateParams', 'entity', 'Employer', 'Country', 'User', 'Auth', '$state',
    function (Principal, $scope, $stateParams, entity, Employer, Country, User, Auth, $state) {

       /* Principal.identity().then(function (account) {
            $scope.account = account;
            User.get({login: $scope.account.login}, function (result) {
                $scope.user = result;
                //console.log($scope.user);
            });
        });*/

        Principal.identity().then(function (account) {
            $scope.account = account;
            User.get({login: $scope.account.login}, function (result) {
                $scope.user = result;
                //console.log($scope.user);
            });
           // console.log("sdasd :"+$scope.account.authorities);
            if($scope.isInArray('ROLE_ADMIN', $scope.account.authorities))
            {
                $scope.userRole = 'ROLE_ADMIN';
            }
            else if($scope.isInArray('ROLE_EMPLOYER', $scope.account.authorities))
            {
                $scope.userRole = 'ROLE_EMPLOYER';
            }
            else if($scope.isInArray('ROLE_JPADMIN', $scope.account.authorities))
            {
                $scope.userRole = 'ROLE_JPADMIN';
            }
            else if($scope.isInArray('ROLE_USER', $scope.account.authorities))
            {
                $scope.userRole = 'ROLE_USER';

            }
        });




        $scope.isInArray = function isInArray(value, array) {
            return array.indexOf(value) > -1;
        };

        $scope.curPass;
        $scope.matchPass = function (pass, conPass) {
            //console.log(pass);
            //console.log(conPass);

            if (pass != conPass) {
                $scope.notMatched = true;
            } else {
                $scope.notMatched = false;
            }

        };
        var onSaveSuccess = function (result) {
            $state.go('#');
        };

        var onSaveError = function (result) {
            //$scope.isSaving = false;
        };

        $scope.success = null;
        $scope.error = null;
        $scope.doNotMatch = null;

        $scope.changePassword = function () {
            $scope.curPass = $scope.currentPassword;
            //console.log( $scope.curPass);
            if ($scope.password !== $scope.confirmPassword) {
                $scope.doNotMatch = 'ERROR';
            } else {
                $scope.doNotMatch = null;
                Auth.changePassword($scope.password).then(function () {
                    $scope.error = null;
                    $scope.success = 'OK';
                    Auth.logout();
                    $state.go('home');
                }).catch(function () {
                    $scope.success = null;
                    $scope.error = 'ERROR';
                });
            }
        };


        /*Employer.get({id: 'my'}, function (result) {
            $scope.employer = result;
            // console.log($scope.employer);
        });*/

        $scope.updatePassword = function () {

        };


    }]);
