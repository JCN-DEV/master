'use strict';

angular.module('stepApp').controller('TempInstGenInfoDialogController',
    ['$scope', '$stateParams', '$q', 'entity', 'TempInstGenInfo', 'InstituteInfo', 'Upazila','User',
        function($scope, $stateParams, $modalInstance, $q, entity, TempInstGenInfo, InstituteInfo, Upazila,User) {

        $scope.tempInstGenInfo = entity;
        /*$scope.instituteinfos = InstituteInfo.query({filter: 'tempinstgeninfo-is-null'});
        $q.all([$scope.tempInstGenInfo.$promise, $scope.instituteinfos.$promise]).then(function() {
            *//*if (!$scope.tempInstGenInfo.instituteInfo.id) {
                return $q.reject();
            }*//*
            return InstituteInfo.get({id : $scope.tempInstGenInfo.instituteInfo.id}).$promise;
        }).then(function(instituteInfo) {
            $scope.instituteinfos.push(instituteInfo);
        });*/
        $scope.upazilas = Upazila.query();
        $scope.load = function(id) {
            TempInstGenInfo.get({id : id}, function(result) {
                $scope.tempInstGenInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:tempInstGenInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            console.log($scope.tempInstGenInfo);
            console.log($scope.instituteInfo);
            $scope.isSaving = true;
            InstituteInfo.save($scope.instituteInfo, onSaveSuccess, onSaveError);
            TempInstGenInfo.save($scope.tempInstGenInfo, onSaveSuccess, onSaveError);
             /*InstituteInfo.save($scope.instituteInfo).then(function(newInstituteInfo){
                            $scope.instituteInfo.instituteInfo=newInstituteInfo;
                            TempInstGenInfo.save($scope.tempInstGenInfo, onSaveSuccess, onSaveError)
                            console.log("ok");
                            });
                            $scope.success = 'OK';*/
            /*$scope.tempInstGenInfo.instituteInfo.user.langKey = "en";
            $scope.tempInstGenInfo.instituteInfo.user.activated = true;
            $scope.tempInstGenInfo.instituteInfo.user.authorities = ["ROLE_USER", "ROLE_MANAGER"];*/
           /* $scope.tempInstGenInfo.instituteInfo.user=null;
            $scope.tempInstGenInfo.instituteInfo.user.langKey = "en";
                        $scope.tempInstGenInfo.instituteInfo.user.activated = true;
                        $scope.tempInstGenInfo.instituteInfo.user.authorities = ["ROLE_USER", "ROLE_MANAGER"];*/

          /*  Auth.createAccount($scope.tempInstGenInfo.instituteInfo.user).then(function (newUser) {
                $scope.employer.user.id = 1;
                //InstituteInfo.save($scope.employer, onSaveSuccess, onSaveError);
                InstituteInfo.save($scope.instituteInfo).then(function(newInstituteInfo){
                $scope.instituteInfo.instituteInfo=newInstituteInfo;
                TempInstGenInfo.save($scope.tempInstGenInfo, onSaveSuccess, onSaveError)
                console.log("ok");
                });
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
            });*/

            /*$scope.isSaving = true;
            if ($scope.tempInstGenInfo.id != null) {
                TempInstGenInfo.update($scope.tempInstGenInfo, onSaveSuccess, onSaveError);
            } else {
                TempInstGenInfo.save($scope.tempInstGenInfo, onSaveSuccess, onSaveError);
            }*/
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
