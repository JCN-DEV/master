'use strict';

angular.module('stepApp').controller('InstituteShopInfoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstituteShopInfo', 'InstituteInfraInfo',
        function($scope, $stateParams, $modalInstance, entity, InstituteShopInfo, InstituteInfraInfo) {

        $scope.instituteShopInfo = entity;
        $scope.instituteinfrainfos = InstituteInfraInfo.query();
        $scope.load = function(id) {
            InstituteShopInfo.get({id : id}, function(result) {
                $scope.instituteShopInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instituteShopInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instituteShopInfo.id != null) {
                InstituteShopInfo.update($scope.instituteShopInfo, onSaveSuccess, onSaveError);
            } else {
                InstituteShopInfo.save($scope.instituteShopInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
