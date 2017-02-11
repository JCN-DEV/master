'use strict';

angular.module('stepApp')
    .controller('RegisterController',
     ['$scope', '$translate', '$timeout', 'Auth',
     function ($scope, $translate, $timeout, Auth) {
        $scope.success = null;
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.errorUserExists = null;
        $scope.registerAccount = {};
        $timeout(function (){angular.element('[ng-model="registerAccount.login"]').focus();});

        $scope.matchPass=function(pass,conPass){

            if(pass != conPass){
                $scope.notMatched=true;
            }else{
                $scope.notMatched=false;
            }

        };

        $scope.register = function () {
            if ($scope.registerAccount.password !== $scope.confirmPassword) {
                $scope.doNotMatch = 'ERROR';
            } else {
                $scope.registerAccount.langKey = $translate.use();
                $scope.doNotMatch = null;
                $scope.error = null;
                $scope.errorUserExists = null;
                $scope.errorEmailExists = null;
                $scope.registerAccount.activated = false;
                $scope.registerAccount.authorities = ["ROLE_USER"];

                Auth.createAccount($scope.registerAccount).then(function () {
                    $scope.success = 'OK';
                }).catch(function (response) {
                    $scope.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        $scope.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        $scope.errorEmailExists = 'ERROR';
                    } else {
                        $scope.error = 'ERROR';
                    }
                });
            }
        };


         $scope.jobSeekerRegister = function () {
            if ($scope.registerAccount.password !== $scope.confirmPassword) {
                $scope.doNotMatch = 'ERROR';
            } else {
                $scope.registerAccount.langKey = $translate.use();
                $scope.doNotMatch = null;
                $scope.error = null;
                $scope.errorUserExists = null;
                $scope.errorEmailExists = null;
                $scope.registerAccount.activated = false;
                $scope.registerAccount.authorities = ["ROLE_JOBSEEKER"];

                Auth.createAccount($scope.registerAccount).then(function () {
                    $scope.success = 'OK';
                }).catch(function (response) {
                    $scope.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        $scope.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        $scope.errorEmailExists = 'ERROR';
                    } else {
                        $scope.error = 'ERROR';
                    }
                });
            }
        };
    }])
    .controller('DeoRegisterController',
     ['$scope', '$translate', '$timeout', 'Auth','District','AccountByDistrict',
     function ($scope, $translate, $timeout, Auth,District,AccountByDistrict) {
        var allDistrict= District.query({page: $scope.page, size: 65}, function(result, headers) { return result;});


        $scope.success = null;
        $scope.districts = allDistrict;
        $scope.error = null;
        $scope.doNotMatch = null;
        $scope.errorUserExists = null;
        $scope.registerAccount = {};
        $scope.deoExist = true;
       // $scope.registerAccount.district={};
        $scope.showMsg = false;
            $timeout(function (){angular.element('[ng-model="registerAccount.login"]').focus();});

        $scope.checkDistrict=function(){
            AccountByDistrict.get({id: $scope.registerAccount.district.id}, function (result) {
                console.log(result);
                if(result.isValid){
                    $scope.showMsg = false;
                    $scope.deoExist = false;
                }else{
                    $scope.showMsg = true;
                    $scope.deoExist = true;
                }
            }, function (response) {

            });
        }
        $scope.matchPass=function(pass,conPass){

            if(pass != conPass){
                $scope.notMatched=true;
            }else{
                $scope.notMatched=false;
            }

        };

        $scope.register = function () {
            if ($scope.registerAccount.password !== $scope.confirmPassword) {
                $scope.doNotMatch = 'ERROR';
            } else {

                $scope.registerAccount.langKey = $translate.use();
                $scope.registerAccount.firstName = "DEO Of"+$scope.registerAccount.district.name;
                $scope.doNotMatch = null;
                $scope.error = null;
                $scope.errorUserExists = null;
                $scope.errorEmailExists = null;
                $scope.registerAccount.activated = true;
                $scope.registerAccount.authorities = ["ROLE_DEO"];
                //$scope.registerAccount.district = null;
                //$scope.registerAccount.district.id = $scope.district.id;
               // console.log('District :'+$scope.registerAccount.district.id);

                Auth.createDeoAccount($scope.registerAccount).then(function () {
                    $scope.success = 'OK';
                }).catch(function (response) {
                    $scope.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        $scope.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        $scope.errorEmailExists = 'ERROR';
                    } else {
                        $scope.error = 'ERROR';
                    }
                });
            }
        };
    }]);
