'use strict';

angular.module('stepApp').controller('UmracSubmoduleSetupDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'UmracSubmoduleSetup', 'UmracModuleSetup',
        function($scope, $stateParams, $state, entity, UmracSubmoduleSetup, UmracModuleSetup) {

        $scope.umracSubmoduleSetup = entity;
        $scope.umracmodulesetups = UmracModuleSetup.query();
        $scope.load = function(id) {
            UmracSubmoduleSetup.get({id : id}, function(result) {
                $scope.umracSubmoduleSetup = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:umracSubmoduleSetupUpdate', result);
            $state.go('umracSubmoduleSetup');
        };

        $scope.save = function () {
            if ($scope.umracSubmoduleSetup.id != null) {
                UmracSubmoduleSetup.update($scope.umracSubmoduleSetup, onSaveFinished);
            } else {
                UmracSubmoduleSetup.save($scope.umracSubmoduleSetup, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $state.go('umracSubmoduleSetup');
        };

        $scope.filterModuleByStatus = function () {
                                    return function (item) {
                                        if (item.status == true)
                                        {
                                            return true;
                                        }
                                        return false;
                                    };
                                };
}]);
