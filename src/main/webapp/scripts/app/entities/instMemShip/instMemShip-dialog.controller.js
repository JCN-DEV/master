'use strict';

angular.module('stepApp').controller('InstMemShipDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstMemShip',
        function($scope, $stateParams, $modalInstance, entity, InstMemShip) {

        $scope.instMemShip = entity;
        $scope.load = function(id) {
            InstMemShip.get({id : id}, function(result) {
                $scope.instMemShip = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instMemShipUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instMemShip.id != null) {
                InstMemShip.update($scope.instMemShip, onSaveSuccess, onSaveError);
            } else {
                InstMemShip.save($scope.instMemShip, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
