'use strict';

angular.module('stepApp').controller('InstBuildingDialogController',

       ['$scope','$rootScope', '$stateParams', '$q', '$state', 'entity', 'InstBuilding', 'Institute',
        function($scope,$rootScope, $stateParams, $q, $state, entity, InstBuilding, Institute) {

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
                $rootScope.setSuccessMessage('stepApp.instBuilding.updated');
            } else {
                InstBuilding.save($scope.instBuilding, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instBuilding.created');
            }
        };

        $scope.clear = function() {
        };
}]);
