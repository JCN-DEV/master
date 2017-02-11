'use strict';

angular.module('stepApp').controller('PrlPayscaleBasicInfoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PrlPayscaleBasicInfo', 'PrlPayscaleInfo',
        function($scope, $stateParams, $modalInstance, entity, PrlPayscaleBasicInfo, PrlPayscaleInfo) {

        $scope.prlPayscaleBasicInfo = entity;
        $scope.prlpayscaleinfos = PrlPayscaleInfo.query();
        $scope.load = function(id) {
            PrlPayscaleBasicInfo.get({id : id}, function(result) {
                $scope.prlPayscaleBasicInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:prlPayscaleBasicInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.prlPayscaleBasicInfo.id != null) {
                PrlPayscaleBasicInfo.update($scope.prlPayscaleBasicInfo, onSaveSuccess, onSaveError);
            } else {
                PrlPayscaleBasicInfo.save($scope.prlPayscaleBasicInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
