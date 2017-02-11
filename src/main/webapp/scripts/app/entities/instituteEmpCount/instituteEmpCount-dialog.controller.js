'use strict';

angular.module('stepApp').controller('InstituteEmpCountDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'InstituteEmpCount', 'Institute',
        function($scope, $stateParams, $modalInstance, $q, entity, InstituteEmpCount, Institute) {

        $scope.instituteEmpCount = entity;
        $scope.institutes = Institute.query({filter: 'instituteempcount-is-null'});
        $q.all([$scope.instituteEmpCount.$promise, $scope.institutes.$promise]).then(function() {
            if (!$scope.instituteEmpCount.institute.id) {
                return $q.reject();
            }
            return Institute.get({id : $scope.instituteEmpCount.institute.id}).$promise;
        }).then(function(institute) {
            $scope.institutes.push(institute);
        });
        $scope.load = function(id) {
            InstituteEmpCount.get({id : id}, function(result) {
                $scope.instituteEmpCount = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instituteEmpCountUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instituteEmpCount.id != null) {
                InstituteEmpCount.update($scope.instituteEmpCount, onSaveSuccess, onSaveError);
            } else {
                InstituteEmpCount.save($scope.instituteEmpCount, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
