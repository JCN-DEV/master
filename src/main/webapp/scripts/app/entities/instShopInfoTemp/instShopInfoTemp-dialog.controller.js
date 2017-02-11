'use strict';

angular.module('stepApp').controller('InstShopInfoTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstShopInfoTemp', 'InstInfraInfo',
        function($scope, $stateParams, $modalInstance, entity, InstShopInfoTemp, InstInfraInfo) {

        $scope.instShopInfoTemp = entity;
        $scope.instinfrainfos = InstInfraInfo.query();
        $scope.load = function(id) {
            InstShopInfoTemp.get({id : id}, function(result) {
                $scope.instShopInfoTemp = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:instShopInfoTempUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.instShopInfoTemp.id != null) {
                InstShopInfoTemp.update($scope.instShopInfoTemp, onSaveFinished);
            } else {
                InstShopInfoTemp.save($scope.instShopInfoTemp, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
