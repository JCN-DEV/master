'use strict';

angular.module('stepApp').controller('OrganizationCategoryDialogController',
    //['$scope', '$stateParams', 'entity','$state', 'OrganizationCategory',

    ['$scope','$rootScope','$stateParams','$state','entity','OrganizationCategory',
        function($scope, $rootScope, $stateParams, $state, entity, OrganizationCategory) {
        console.log($stateParams.id);
        $scope.organizationCategory = {};
            OrganizationCategory.get({id : $stateParams.id}, function(result) {
                console.log(result);
                $scope.organizationCategory = result;
            });
        /*$scope.load = function(id) {

        };*/

        var onSaveSuccess = function (result) {
            //$scope.$emit('stepApp:organizationCategoryUpdate', result);
            //$modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('organizationCategory', null, { reload: true });
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            //console.log($scope.organizationCategory);
            if ($scope.organizationCategory.id != null) {
                OrganizationCategory.update($scope.organizationCategory, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('Organization Category has been successfully updated');
            } else {
                OrganizationCategory.save($scope.organizationCategory, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('Organization Category has been successfully saved');
            }
        };

        $scope.clear = function() {
           // $modalInstance.dismiss('cancel');
        };
}]
//]
);
