'use strict';

angular.module('stepApp')
    .controller('RisRegistrationController',
    ['$scope', '$state', '$rootScope', 'userregistration',
    function ($scope, $state, $rootScope, userregistration) {
       $scope.risRegistration = [];

        $scope.user = {} ;
        $scope.usernametemp = "shafiqayonrr";
        $scope.pwtemp = "twinmos4";
        $scope.firsttemp = "Shafiqurr";
        $scope.secondtemp = "Rahmann";
        $scope.emailtemp = "shafiqur.rahman.ayonn@gmail.com";

        /*$scope.register= function(){
                userregistration.get({
                username:$scope.risRegistration.login,
                password:$scope.risRegistration.password,
                firstname:$scope.risRegistration.firstName,
                lastname:$scope.risRegistration.lastName,
                email:$scope.risRegistration.emailRegistration},
                function(result){
                    $scope.temp = result;
                    console.log(result);
                })
        }*/

                 $scope.register= function(){
                        console.log("Ris Register");
                        console.log($scope.risRegistration.login);
                        console.log($scope.risRegistration.password);
                        console.log($scope.risRegistration.firstName);
                        console.log($scope.risRegistration.lastName);
                        console.log($scope.risRegistration.emailRegistration);

                        userregistration.get({
                            username:$scope.risRegistration.login,
                            password:$scope.risRegistration.password,
                            firstname:$scope.risRegistration.firstName,
                            lastname:$scope.risRegistration.lastName,
                            email:$scope.risRegistration.emailRegistration
                        },function(result){

                            $scope.temp = result;
                            console.log(result);
                            console.log("<==========> Error <===========>");
                            $('#registerComplete').modal('show');
                        });
                 }



        /*$scope.register= function()
        {

        console.log("<<<<<<<<>>>>>>>>>");
        console.log($scope.risRegistration);
        console.log("<<<<<<<<>>>>>>>>>");
        *//*risNewAppForms/userregistration/:username/:password/:firstname/:lastname/:email*//*
        userregistration.get({username:$scope.risRegistration.username,
        password:$scope.risRegistration.password,
        firstname:$scope.risRegistration.firstName,
        lastname:$scope.risRegistration.lastName,
        email:$scope.risRegistration.emailRegistration}, function(result){
            $scope.temp = result;
              console.log(result);

        })

        }*/


        $scope.clear = function () {
            $scope.risRegistration = {
                firstName: null,
                lastName: null,
                login: null,
                emailRegistration: null,
                password: null,
                confirmPassword: null
            };
        };
    }]);
