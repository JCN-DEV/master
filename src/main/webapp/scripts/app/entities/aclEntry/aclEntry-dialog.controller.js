'use strict';

angular.module('stepApp').controller('AclEntryDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'AclEntry', 'AclObjectIdentity', 'AclSid',
        function($scope, $stateParams, $modalInstance, entity, AclEntry, AclObjectIdentity, AclSid) {

        $scope.aclEntry = entity;
        $scope.aclobjectidentitys = AclObjectIdentity.query();
        $scope.aclsids = AclSid.query();
        $scope.load = function(id) {
            AclEntry.get({id : id}, function(result) {
                $scope.aclEntry = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:aclEntryUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.aclEntry.id != null) {
                AclEntry.update($scope.aclEntry, onSaveSuccess, onSaveError);
            } else {
                AclEntry.save($scope.aclEntry, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
