'use strict';

angular.module('stepApp').controller('InstPlayGroundInfoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstPlayGroundInfo', 'InstInfraInfo',
        function($scope, $stateParams, $modalInstance, entity, InstPlayGroundInfo, InstInfraInfo) {

        $scope.instPlayGroundInfo = entity;
        $scope.instinfrainfos = InstInfraInfo.query();
        $scope.load = function(id) {
            InstPlayGroundInfo.get({id : id}, function(result) {
                $scope.instPlayGroundInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instPlayGroundInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instPlayGroundInfo.id != null) {
                InstPlayGroundInfo.update($scope.instPlayGroundInfo, onSaveSuccess, onSaveError);
            } else {
                InstPlayGroundInfo.save($scope.instPlayGroundInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
