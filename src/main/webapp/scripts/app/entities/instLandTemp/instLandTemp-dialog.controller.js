'use strict';

angular.module('stepApp').controller('InstLandTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstLandTemp',
        function($scope, $stateParams, $modalInstance, entity, InstLandTemp) {

        $scope.instLandTemp = entity;
        $scope.load = function(id) {
            InstLandTemp.get({id : id}, function(result) {
                $scope.instLandTemp = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:instLandTempUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.instLandTemp.id != null) {
                InstLandTemp.update($scope.instLandTemp, onSaveFinished);
            } else {
                InstLandTemp.save($scope.instLandTemp, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
