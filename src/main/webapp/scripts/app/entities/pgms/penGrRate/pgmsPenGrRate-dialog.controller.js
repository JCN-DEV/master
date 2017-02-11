'use strict';

angular.module('stepApp').controller('PgmsPenGrRateDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'PgmsPenGrRate',
        function($scope, $stateParams, $state, entity, PgmsPenGrRate) {

        $scope.pgmsPenGrRate = entity;

        $scope.load = function(id) {
            PgmsPenGrRate.get({id : id}, function(result) {
                $scope.pgmsPenGrRate = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:pgmsPenGrRateUpdate', result);
            $scope.isSaving = false;
            $state.go("pgmsPenGrRate");
        };

        $scope.save = function () {
            if ($scope.pgmsPenGrRate.id != null) {
                PgmsPenGrRate.update($scope.pgmsPenGrRate, onSaveFinished);
            } else {
                PgmsPenGrRate.save($scope.pgmsPenGrRate, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $state.dismiss('cancel');
        };
}]);
