'use strict';

angular.module('stepApp').controller('AllowanceSetupDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'AllowanceSetup',
        function($scope, $stateParams, $state, entity, AllowanceSetup) {

        $scope.allowanceSetup = entity;
        $scope.load = function(id) {
            AllowanceSetup.get({id : id}, function(result) {
                $scope.allowanceSetup = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:allowanceSetupUpdate', result);
            $state.go('allowanceSetup');
        };

        $scope.save = function () {
            if ($scope.allowanceSetup.id != null) {
                AllowanceSetup.update($scope.allowanceSetup, onSaveFinished);
            } else {
                AllowanceSetup.save($scope.allowanceSetup, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $state.go('allowanceSetup');
        };
}]);
