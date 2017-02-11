'use strict';

angular.module('stepApp').controller('InsAcademicInfoTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'InsAcademicInfoTemp', 'Institute',
        function($scope, $stateParams, $modalInstance, $q, entity, InsAcademicInfoTemp, Institute) {

        $scope.insAcademicInfoTemp = entity;
        $scope.institutes = Institute.query({filter: 'insacademicinfotemp-is-null'});
        $q.all([$scope.insAcademicInfoTemp.$promise, $scope.institutes.$promise]).then(function() {
            if (!$scope.insAcademicInfoTemp.institute.id) {
                return $q.reject();
            }
            return Institute.get({id : $scope.insAcademicInfoTemp.institute.id}).$promise;
        }).then(function(institute) {
            $scope.institutes.push(institute);
        });
        $scope.load = function(id) {
            InsAcademicInfoTemp.get({id : id}, function(result) {
                $scope.insAcademicInfoTemp = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:insAcademicInfoTempUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.insAcademicInfoTemp.id != null) {
                InsAcademicInfoTemp.update($scope.insAcademicInfoTemp, onSaveFinished);
            } else {
                InsAcademicInfoTemp.save($scope.insAcademicInfoTemp, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
