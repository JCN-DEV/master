'use strict';

angular.module('stepApp').controller('AclObjectIdentityDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'AclObjectIdentity', 'AclClass', 'AclSid',
        function($scope, $stateParams, $modalInstance, $q, entity, AclObjectIdentity, AclClass, AclSid) {

        $scope.aclObjectIdentity = entity;
        $scope.aclclasss = AclClass.query();
        $scope.aclsids = AclSid.query();
        $scope.parents = AclObjectIdentity.query({filter: 'aclobjectidentity-is-null'});
        $q.all([$scope.aclObjectIdentity.$promise, $scope.parents.$promise]).then(function() {
            if (!$scope.aclObjectIdentity.parent.id) {
                return $q.reject();
            }
            return AclObjectIdentity.get({id : $scope.aclObjectIdentity.parent.id}).$promise;
        }).then(function(parent) {
            $scope.parents.push(parent);
        });
        $scope.load = function(id) {
            AclObjectIdentity.get({id : id}, function(result) {
                $scope.aclObjectIdentity = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:aclObjectIdentityUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.aclObjectIdentity.id != null) {
                AclObjectIdentity.update($scope.aclObjectIdentity, onSaveSuccess, onSaveError);
            } else {
                AclObjectIdentity.save($scope.aclObjectIdentity, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
