'use strict';

angular.module('stepApp').controller('AclSidDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'AclSid',
        function($scope, $stateParams, $modalInstance, entity, AclSid) {

        $scope.aclSid = entity;
        $scope.load = function(id) {
            AclSid.get({id : id}, function(result) {
                $scope.aclSid = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:aclSidUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.aclSid.id != null) {
                AclSid.update($scope.aclSid, onSaveSuccess, onSaveError);
            } else {
                AclSid.save($scope.aclSid, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
