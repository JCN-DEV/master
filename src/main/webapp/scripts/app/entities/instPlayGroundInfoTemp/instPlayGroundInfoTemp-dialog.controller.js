'use strict';

angular.module('stepApp').controller('InstPlayGroundInfoTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstPlayGroundInfoTemp', 'InstInfraInfo',
        function($scope, $stateParams, $modalInstance, entity, InstPlayGroundInfoTemp, InstInfraInfo) {

        $scope.instPlayGroundInfoTemp = entity;
        $scope.instinfrainfos = InstInfraInfo.query();
        $scope.load = function(id) {
            InstPlayGroundInfoTemp.get({id : id}, function(result) {
                $scope.instPlayGroundInfoTemp = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:instPlayGroundInfoTempUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.instPlayGroundInfoTemp.id != null) {
                InstPlayGroundInfoTemp.update($scope.instPlayGroundInfoTemp, onSaveFinished);
            } else {
                InstPlayGroundInfoTemp.save($scope.instPlayGroundInfoTemp, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
