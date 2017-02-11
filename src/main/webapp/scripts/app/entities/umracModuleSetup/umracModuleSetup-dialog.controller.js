'use strict';

angular.module('stepApp').controller('UmracModuleSetupDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'UmracModuleSetup',
        function($scope, $stateParams, $modalInstance, entity, UmracModuleSetup) {

        $scope.umracModuleSetup = entity;

        $scope.load = function(id) {

            UmracModuleSetup.get({id : id}, function(result) {
                $scope.umracModuleSetup = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:umracModuleSetupUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.umracModuleSetup.id != null) {
                UmracModuleSetup.update($scope.umracModuleSetup, onSaveFinished);
            } else {
                UmracModuleSetup.save($scope.umracModuleSetup, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
