'use strict';

angular.module('stepApp').controller('DeoDialogController',
    ['$scope', '$stateParams', '$q', 'Deo', 'District','Auth','DeoHistLog',
        function($scope, $stateParams, $q, Deo, District,Auth,DeoHistLog) {
        $scope.deo = {};
        $scope.registerAccount = {};
        $scope.deoHistLog = {};
            if($stateParams.id){
                Deo.get({id: $stateParams.id}, function(result){
                    $scope.deo = result;
                });
            };

        var allDistrict= District.query({page: $scope.page, size: 65}, function(result, headers) { return result;});

        $scope.districts = allDistrict;
        /*$scope.load = function(id) {
            Deo.get({id : id}, function(result) {
                $scope.deo = result;
            });
        };*/

        var onSaveSuccess = function (result) {
            $scope.deoHistLog.deo = result;
            DeoHistLog.save($scope.deoHistLog);
            $scope.isSaving = false;
           // location.reload();
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        var onDeoLogSaveSuccess = function (result) {

        $scope.isSaving = false;
        location.reload();
        };

        var onDeoLogSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.deo.id != null) {
                Deo.update($scope.deo, onSaveSuccess, onSaveError);
            } else {
                if ($scope.registerAccount.password !== $scope.confirmPassword) {
                    $scope.doNotMatch = 'ERROR';
                } else {

                    $scope.registerAccount.langKey = 'en';
                    //$scope.registerAccount.firstName = "DEO Of "+$scope.deoHistLog.district.name;
                    $scope.registerAccount.firstName = "DEO";
                    //$scope.registerAccount.district = scope.deoHistLog.district;
                    $scope.doNotMatch = null;
                    $scope.error = null;
                    $scope.errorUserExists = null;
                    $scope.errorEmailExists = null;
                    $scope.registerAccount.activated = true;
                    $scope.registerAccount.authorities = ["ROLE_DEO"];
                    //$scope.registerAccount.district = null;
                    //$scope.registerAccount.district.id = $scope.district.id;
                    // console.log('District :'+$scope.registerAccount.district.id);

                    Auth.createDeoAccount($scope.registerAccount).then(function (result) {
                        $scope.success = 'OK';
                        $scope.deo.user = result;
                        $scope.deo.email = $scope.registerAccount.email;
                        //console.log('comes inside success');
                        Deo.save($scope.deo, function (result2) {
                            $scope.deoHistLog.deo = result2;
                            DeoHistLog.save($scope.deoHistLog,onDeoLogSaveSuccess);
                        });
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
                //Deo.save($scope.deo, onSaveSuccess, onSaveError);
            }
        };

            $scope.matchPass=function(pass,conPass){

                if(pass != conPass){
                    $scope.notMatched=true;
                }else{
                    $scope.notMatched=false;
                }

            };
        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
        };
}]);
