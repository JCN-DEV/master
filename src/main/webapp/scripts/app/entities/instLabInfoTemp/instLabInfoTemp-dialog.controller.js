'use strict';

angular.module('stepApp').controller('InstLabInfoTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstLabInfoTemp', 'InstInfraInfo',
        function($scope, $stateParams, $modalInstance, entity, InstLabInfoTemp, InstInfraInfo) {

        $scope.instLabInfoTemp = entity;
        $scope.instinfrainfos = InstInfraInfo.query();
        $scope.load = function(id) {
            InstLabInfoTemp.get({id : id}, function(result) {
                $scope.instLabInfoTemp = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:instLabInfoTempUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.instLabInfoTemp.id != null) {
                InstLabInfoTemp.update($scope.instLabInfoTemp, onSaveFinished);
            } else {
                InstLabInfoTemp.save($scope.instLabInfoTemp, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
