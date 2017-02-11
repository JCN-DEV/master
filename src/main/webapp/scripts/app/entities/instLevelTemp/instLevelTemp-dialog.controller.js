'use strict';

angular.module('stepApp').controller('InstLevelTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstLevelTemp',
        function($scope, $stateParams, $modalInstance, entity, InstLevelTemp) {

        $scope.instLevelTemp = entity;
        $scope.load = function(id) {
            InstLevelTemp.get({id : id}, function(result) {
                $scope.instLevelTemp = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:instLevelTempUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.instLevelTemp.id != null) {
                InstLevelTemp.update($scope.instLevelTemp, onSaveFinished);
            } else {
                InstLevelTemp.save($scope.instLevelTemp, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
