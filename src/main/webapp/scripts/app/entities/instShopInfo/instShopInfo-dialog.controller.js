'use strict';

angular.module('stepApp').controller('InstShopInfoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstShopInfo', 'InstInfraInfo',
        function($scope, $stateParams, $modalInstance, entity, InstShopInfo, InstInfraInfo) {

        $scope.instShopInfo = entity;
        $scope.instinfrainfos = InstInfraInfo.query();
        $scope.load = function(id) {
            InstShopInfo.get({id : id}, function(result) {
                $scope.instShopInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instShopInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instShopInfo.id != null) {
                InstShopInfo.update($scope.instShopInfo, onSaveSuccess, onSaveError);
            } else {
                InstShopInfo.save($scope.instShopInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
