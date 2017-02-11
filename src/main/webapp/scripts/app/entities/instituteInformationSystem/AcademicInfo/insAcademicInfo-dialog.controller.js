'use strict';

angular.module('stepApp').controller('InsAcademicInfoDialogController',
    ['$scope','$rootScope', '$stateParams', '$state','$q', 'entity', 'InsAcademicInfo', 'Institute','InstGenInfo',
        function($scope, $rootScope,$stateParams, $state, $q, entity, InsAcademicInfo, Institute,InstGenInfo) {

        $scope.insAcademicInfo = entity;
        $scope.insAcademicInfo.InstGenInfo =InstGenInfo.get({id : 0});
        /*$scope.institutes = Institute.query({filter: 'insacademicinfo-is-null'});
        $q.all([$scope.insAcademicInfo.$promise, $scope.institutes.$promise]).then(function() {
            if (!$scope.insAcademicInfo.institute.id) {
                return $q.reject();
            }
            return Institute.get({id : $scope.insAcademicInfo.institute.id}).$promise;
        }).then(function(institute) {
            $scope.institutes.push(institute);
        });*/
       /* $scope.load = function(id) {
            console.log("id:--- "+ id);
            InsAcademicInfo.get({id : id}, function(result) {
                $scope.insAcademicInfo = result;
            });
        };*/

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:insAcademicInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('instituteInfo.academicInfo',{},{reload:true});
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
            //$rootScope.setErrorMessage('stepApp.insAcademicInfo.Error');
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.insAcademicInfo.id != null) {
                if($scope.insAcademicInfo.status==1){
                    console.log('  after approved');
                    $scope.insAcademicInfo.status=3
                    InsAcademicInfo.update($scope.insAcademicInfo, onSaveSuccess, onSaveError);
                     $rootScope.setWarningMessage('stepApp.insAcademicInfo.updated');
                }else{
                    $scope.insAcademicInfo.status=0;
                    console.log('  before approved');
                    InsAcademicInfo.update($scope.insAcademicInfo, onSaveSuccess, onSaveError);
                    $rootScope.setWarningMessage('stepApp.insAcademicInfo.updated');
                }
               // $scope.insAcademicInfo.status=2;
                /*InsAcademicInfo.update($scope.insAcademicInfo, onSaveSuccess, onSaveError);*/
            } else {
                $scope.insAcademicInfo.status=0;
                InsAcademicInfo.save($scope.insAcademicInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.insAcademicInfo.created');
            }
        };

        $scope.clear = function() {
             $scope.insAcademicInfo = null;
        };
}]);
