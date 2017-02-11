'use strict';

angular.module('stepApp').controller('UmracActionSetupDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'UmracActionSetup',
        function($scope, $stateParams, $state, entity, UmracActionSetup) {

        $scope.umracActionSetup = entity;
        $scope.load = function(id) {
            UmracActionSetup.get({id : id}, function(result) {
                $scope.umracActionSetup = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:umracActionSetupUpdate', result);
            //$modalInstance.close(result);
            $state.go('umracActionSetup');
        };

        $scope.save = function () {
            if ($scope.umracActionSetup.id != null) {
                UmracActionSetup.update($scope.umracActionSetup, onSaveFinished);
            } else {
                UmracActionSetup.save($scope.umracActionSetup, onSaveFinished);
            }
        };

        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
            $state.go('umracActionSetup');
        };
}]);
