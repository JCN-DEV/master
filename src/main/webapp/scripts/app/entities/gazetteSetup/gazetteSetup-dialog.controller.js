'use strict';

angular.module('stepApp').controller('GazetteSetupDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'GazetteSetup',
        function($scope, $stateParams, $state, entity, GazetteSetup) {

        $scope.gazetteSetup = entity;
        $scope.load = function(id) {
            GazetteSetup.get({id : id}, function(result) {
                $scope.gazetteSetup = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:gazetteSetupUpdate', result);
            $state.go('gazetteSetup');
        };

        $scope.save = function () {
            if ($scope.gazetteSetup.id != null) {
                GazetteSetup.update($scope.gazetteSetup, onSaveFinished);
            } else {
                GazetteSetup.save($scope.gazetteSetup, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $state.go('gazetteSetup');
        };
}]);
