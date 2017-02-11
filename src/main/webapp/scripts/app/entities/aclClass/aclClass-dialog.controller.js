'use strict';

angular.module('stepApp').controller('AclClassDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'AclClass',
        function($scope, $stateParams, $modalInstance, entity, AclClass) {

        $scope.aclClass = entity;
        $scope.load = function(id) {
            AclClass.get({id : id}, function(result) {
                $scope.aclClass = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:aclClassUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.aclClass.id != null) {
                AclClass.update($scope.aclClass, onSaveSuccess, onSaveError);
            } else {
                AclClass.save($scope.aclClass, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
