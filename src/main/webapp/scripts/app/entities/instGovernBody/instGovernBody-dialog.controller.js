'use strict';

angular.module('stepApp').controller('InstGovernBodyDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstGovernBody', 'Institute',
        function($scope, $stateParams, $modalInstance, entity, InstGovernBody, Institute) {

        $scope.instGovernBody = entity;
        $scope.institutes = Institute.query();
        $scope.load = function(id) {
            InstGovernBody.get({id : id}, function(result) {
                $scope.instGovernBody = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instGovernBodyUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instGovernBody.id != null) {
                InstGovernBody.update($scope.instGovernBody, onSaveSuccess, onSaveError);
            } else {
                InstGovernBody.save($scope.instGovernBody, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
