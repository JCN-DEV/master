'use strict';

angular.module('stepApp').controller('UmracSubmoduleSetupDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'UmracSubmoduleSetup', 'UmracModuleSetup',
        function($scope, $stateParams, $modalInstance, entity, UmracSubmoduleSetup, UmracModuleSetup) {

        $scope.umracSubmoduleSetup = entity;
        $scope.umracmodulesetups = UmracModuleSetup.query();
        $scope.load = function(id) {
            UmracSubmoduleSetup.get({id : id}, function(result) {
                $scope.umracSubmoduleSetup = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:umracSubmoduleSetupUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.umracSubmoduleSetup.id != null) {
                UmracSubmoduleSetup.update($scope.umracSubmoduleSetup, onSaveFinished);
            } else {
                UmracSubmoduleSetup.save($scope.umracSubmoduleSetup, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
