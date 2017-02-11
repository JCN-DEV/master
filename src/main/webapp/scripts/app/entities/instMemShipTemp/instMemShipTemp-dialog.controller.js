'use strict';

angular.module('stepApp').controller('InstMemShipTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstMemShipTemp',
        function($scope, $stateParams, $modalInstance, entity, InstMemShipTemp) {

        $scope.instMemShipTemp = entity;
        $scope.load = function(id) {
            InstMemShipTemp.get({id : id}, function(result) {
                $scope.instMemShipTemp = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:instMemShipTempUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.instMemShipTemp.id != null) {
                InstMemShipTemp.update($scope.instMemShipTemp, onSaveFinished);
            } else {
                InstMemShipTemp.save($scope.instMemShipTemp, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
