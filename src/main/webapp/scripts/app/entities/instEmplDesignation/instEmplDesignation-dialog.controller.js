'use strict';

angular.module('stepApp').controller('InstEmplDesignationDialogController',
    ['$scope', '$stateParams', '$rootScope', '$modalInstance', 'entity', 'InstEmplDesignation',
        function($scope, $stateParams, $rootScope, $modalInstance, entity, InstEmplDesignation) {

        $scope.instEmplDesignation = {};
            InstEmplDesignation.get({id : $stateParams.id}, function(result) {
                $scope.instEmplDesignation = result;
            });

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmplDesignationUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instEmplDesignation.id != null) {
                InstEmplDesignation.update($scope.instEmplDesignation, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.instEmplDesignation.updated');
            } else {
                InstEmplDesignation.save($scope.instEmplDesignation, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.instEmplDesignation.created');
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
