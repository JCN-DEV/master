'use strict';

angular.module('stepApp').controller('InsAcademicInfoDialogController',
    ['$scope', '$stateParams', '$state','$q', 'entity', 'InsAcademicInfo', 'Institute',
        function($scope, $stateParams, $state, $q, entity, InsAcademicInfo, Institute) {

        $scope.insAcademicInfo = entity;
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
            $state.go('instGenInfo.insAcademicInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.insAcademicInfo.id != null) {
                $scope.insAcademicInfo.status=2;
                InsAcademicInfo.update($scope.insAcademicInfo, onSaveSuccess, onSaveError);
            } else {
                $scope.insAcademicInfo.status=0;
                InsAcademicInfo.save($scope.insAcademicInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
        $scope.insAcademicInfo = null;
        };
}]);
