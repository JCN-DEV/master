'use strict';

angular.module('stepApp').controller('InstGovernBodyTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstGovernBodyTemp', 'Institute',
        function($scope, $stateParams, $modalInstance, entity, InstGovernBodyTemp, Institute) {

        $scope.instGovernBodyTemp = entity;
        $scope.institutes = Institute.query();
        $scope.load = function(id) {
            InstGovernBodyTemp.get({id : id}, function(result) {
                $scope.instGovernBodyTemp = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:instGovernBodyTempUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.instGovernBodyTemp.id != null) {
                InstGovernBodyTemp.update($scope.instGovernBodyTemp, onSaveFinished);
            } else {
                InstGovernBodyTemp.save($scope.instGovernBodyTemp, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
