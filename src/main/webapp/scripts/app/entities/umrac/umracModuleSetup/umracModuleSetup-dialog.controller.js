'use strict';

angular.module('stepApp').controller('UmracModuleSetupDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'UmracModuleSetup',
        function($scope, $stateParams, $state, entity, UmracModuleSetup) {

        $scope.umracModuleSetup = entity;

        $scope.load = function(id) {

            UmracModuleSetup.get({id : id}, function(result) {
                $scope.umracModuleSetup = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:umracModuleSetupUpdate', result);
           //$modalInstance.close(result);
            $state.go('umracModuleSetup');
        };

        $scope.save = function () {
            if ($scope.umracModuleSetup.id != null) {
                UmracModuleSetup.update($scope.umracModuleSetup, onSaveFinished);
            } else {
                UmracModuleSetup.save($scope.umracModuleSetup, onSaveFinished);
            }
        };

        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
            $state.go('umracModuleSetup');
        };
}]);
