'use strict';

angular.module('stepApp').controller('JobTypeDialogController',
    ['$scope','$rootScope', '$stateParams', '$state', 'entity', 'JobType',
        function($scope, $rootScope, $stateParams, $state, entity, JobType) {

        $scope.jobType = entity;
        $scope.load = function(id) {
            JobType.get({id : id}, function(result) {
                $scope.jobType = result;
            });
        };

        var onSaveSuccess = function (result) {
           // $scope.$emit('stepApp:jobTypeUpdate', result);
           // $modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('jobType', null, { reload: true });
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.jobType.id != null) {
                JobType.update($scope.jobType, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.jobType.updated');
            } else {
                JobType.save($scope.jobType, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.jobType.created');
            }
        };

        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
        };
}]);
