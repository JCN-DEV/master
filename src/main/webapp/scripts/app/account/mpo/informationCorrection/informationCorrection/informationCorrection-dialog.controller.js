'use strict';

angular.module('stepApp').controller('InformationCorrectionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InformationCorrection', 'InstEmployee', 'InstEmplDesignation',
        function($scope, $stateParams, $modalInstance, entity, InformationCorrection, InstEmployee, InstEmplDesignation) {

        $scope.informationCorrection = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.instempldesignations = InstEmplDesignation.query();
        $scope.load = function(id) {
            InformationCorrection.get({id : id}, function(result) {
                $scope.informationCorrection = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:informationCorrectionUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.informationCorrection.id != null) {
                InformationCorrection.update($scope.informationCorrection, onSaveSuccess, onSaveError);
            } else {
                InformationCorrection.save($scope.informationCorrection, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
