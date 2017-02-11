'use strict';

angular.module('stepApp').controller('InstBuildingDialogController',
    ['$scope', '$stateParams', '$q', '$state', 'entity', 'InstBuilding', 'Institute',
        function($scope, $stateParams, $q, $state, entity, InstBuilding, Institute) {

        $scope.instBuilding = entity;
        $scope.load = function(id) {
            InstBuilding.get({id : id}, function(result) {
                $scope.instBuilding = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instBuildingUpdate', result);
            $state.go('instGenInfo.infrastructureInfo');
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instBuilding.id != null) {
                InstBuilding.update($scope.instBuilding, onSaveSuccess, onSaveError);
            } else {
                InstBuilding.save($scope.instBuilding, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
        };
}]);
