'use strict';

angular.module('stepApp').controller('NameCnclApplicationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'NameCnclApplication', 'InstEmployee',
        function($scope, $stateParams, $modalInstance, entity, NameCnclApplication, InstEmployee) {

        $scope.nameCnclApplication = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.load = function(id) {
            NameCnclApplication.get({id : id}, function(result) {
                $scope.nameCnclApplication = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:nameCnclApplicationUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.nameCnclApplication.id != null) {
                NameCnclApplication.update($scope.nameCnclApplication, onSaveSuccess, onSaveError);
            } else {
                NameCnclApplication.save($scope.nameCnclApplication, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
