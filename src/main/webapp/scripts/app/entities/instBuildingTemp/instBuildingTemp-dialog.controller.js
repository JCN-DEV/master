'use strict';

angular.module('stepApp').controller('InstBuildingTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstBuildingTemp',
        function($scope, $stateParams, $modalInstance, entity, InstBuildingTemp) {

        $scope.instBuildingTemp = entity;
        $scope.load = function(id) {
            InstBuildingTemp.get({id : id}, function(result) {
                $scope.instBuildingTemp = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:instBuildingTempUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.instBuildingTemp.id != null) {
                InstBuildingTemp.update($scope.instBuildingTemp, onSaveFinished);
            } else {
                InstBuildingTemp.save($scope.instBuildingTemp, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
