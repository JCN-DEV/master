'use strict';

angular.module('stepApp').controller('InstLabInfoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstLabInfo', 'InstInfraInfo',
        function($scope, $stateParams, $modalInstance, entity, InstLabInfo, InstInfraInfo) {

        $scope.instLabInfo = entity;
        $scope.instinfrainfos = InstInfraInfo.query();
        $scope.load = function(id) {
            InstLabInfo.get({id : id}, function(result) {
                $scope.instLabInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instLabInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instLabInfo.id != null) {
                InstLabInfo.update($scope.instLabInfo, onSaveSuccess, onSaveError);
            } else {
                InstLabInfo.save($scope.instLabInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
