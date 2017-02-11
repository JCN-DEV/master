'use strict';

angular.module('stepApp').controller('OrganizationTypeDialogController',
    ['$scope', '$rootScope', '$stateParams','$state', 'entity', 'OrganizationType',
        function($scope, $rootScope, $stateParams,$state, entity, OrganizationType) {

        $scope.organizationType = {};
        $scope.organizationType.status = true;
        /*$scope.load = function(id) {

        };*/

        OrganizationType.get({id : $stateParams.id}, function(result) {
            $scope.organizationType = result;
        });

        var onSaveSuccess = function (result) {
             //$scope.$emit('stepApp:organizationTypeUpdate', result);
            //$modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('organizationType', null, { reload: true });
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.organizationType.id != null) {
                OrganizationType.update($scope.organizationType, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('Organization Type has been successfully updated');
            } else {
                OrganizationType.save($scope.organizationType, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('Organization Type has been successfully saved');
            }
        };

        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
        };
}]);
