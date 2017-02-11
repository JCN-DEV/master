'use strict';

angular.module('stepApp').controller('InstComiteFormationTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstComiteFormationTemp', 'InstMemShip',
        function($scope, $stateParams, $modalInstance, entity, InstComiteFormationTemp, InstMemShip) {

        $scope.instComiteFormationTemp = entity;
        $scope.instmemships = InstMemShip.query();
        $scope.load = function(id) {
            InstComiteFormationTemp.get({id : id}, function(result) {
                $scope.instComiteFormationTemp = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:instComiteFormationTempUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.instComiteFormationTemp.id != null) {
                InstComiteFormationTemp.update($scope.instComiteFormationTemp, onSaveFinished);
            } else {
                InstComiteFormationTemp.save($scope.instComiteFormationTemp, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
