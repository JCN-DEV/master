'use strict';

angular.module('stepApp').controller('UmracRoleSetupDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'UmracRoleSetup',
        function($scope, $stateParams, $modalInstance, entity, UmracRoleSetup) {

        $scope.umracRoleSetup = entity;
        $scope.load = function(id) {
            UmracRoleSetup.get({id : id}, function(result) {
                $scope.umracRoleSetup = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:umracRoleSetupUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.umracRoleSetup.id != null) {
                UmracRoleSetup.update($scope.umracRoleSetup, onSaveFinished);
            } else {
                UmracRoleSetup.save($scope.umracRoleSetup, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
