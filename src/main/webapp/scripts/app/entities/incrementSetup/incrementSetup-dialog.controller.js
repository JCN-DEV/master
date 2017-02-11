'use strict';

angular.module('stepApp').controller('IncrementSetupDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'IncrementSetup', 'PayScale',
        function($scope, $stateParams, $state, entity, IncrementSetup, PayScale) {

        $scope.incrementSetup = entity;
        $scope.payscales = PayScale.query();
        $scope.load = function(id) {
            IncrementSetup.get({id : id}, function(result) {
                $scope.incrementSetup = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:incrementSetupUpdate', result);
            $state.go('incrementSetup');
        };

        $scope.save = function () {
            if ($scope.incrementSetup.id != null) {
                IncrementSetup.update($scope.incrementSetup, onSaveFinished);
            } else {
                IncrementSetup.save($scope.incrementSetup, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $state.go('incrementSetup');
        };
}]);
