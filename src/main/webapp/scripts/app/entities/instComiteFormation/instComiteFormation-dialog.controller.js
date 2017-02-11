'use strict';

angular.module('stepApp').controller('InstComiteFormationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstComiteFormation', 'InstMemShip',
        function($scope, $stateParams, $modalInstance, entity, InstComiteFormation, InstMemShip) {

        $scope.instComiteFormation = entity;
        $scope.instmemships = InstMemShip.query();
        $scope.load = function(id) {
            InstComiteFormation.get({id : id}, function(result) {
                $scope.instComiteFormation = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instComiteFormationUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instComiteFormation.id != null) {
                InstComiteFormation.update($scope.instComiteFormation, onSaveSuccess, onSaveError);
            } else {
                InstComiteFormation.save($scope.instComiteFormation, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
