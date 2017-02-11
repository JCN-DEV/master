'use strict';

angular.module('stepApp').controller('UmracIdentitySetupDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'UmracIdentitySetup',
        function($scope, $stateParams, $state, entity, UmracIdentitySetup) {

        $scope.umracIdentitySetup = entity;
        $scope.load = function(id) {
            UmracIdentitySetup.get({id : id}, function(result) {
                $scope.umracIdentitySetup = result;

            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:umracIdentitySetupUpdate', result);
            $state.go('umracIdentitySetup');
        };

        $scope.save = function () {
            if ($scope.umracIdentitySetup.id != null) {
                UmracIdentitySetup.update($scope.umracIdentitySetup, onSaveFinished);
            } else {
                UmracIdentitySetup.save($scope.umracIdentitySetup, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $state.go('umracIdentitySetup');
        };
}]);
