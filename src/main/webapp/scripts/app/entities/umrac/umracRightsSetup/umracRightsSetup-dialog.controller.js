'use strict';

angular.module('stepApp').controller('UmracRightsSetupDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'UmracRightsSetup',
        function($scope, $stateParams, $modalInstance, entity, UmracRightsSetup) {

        $scope.umracRightsSetup = entity;
        $scope.load = function(id) {
            UmracRightsSetup.get({id : id}, function(result) {
                $scope.umracRightsSetup = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:umracRightsSetupUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.umracRightsSetup.id != null) {
                UmracRightsSetup.update($scope.umracRightsSetup, onSaveFinished);
            } else {
                UmracRightsSetup.save($scope.umracRightsSetup, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
