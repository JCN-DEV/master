'use strict';

angular.module('stepApp').controller('NotificationStepDialogController',
    ['$rootScope','$scope', '$stateParams', '$modalInstance', 'entity', 'NotificationStep',
        function($rootScope, $scope, $stateParams, $modalInstance, entity, NotificationStep) {

        $scope.notificationStep = entity;
        $scope.load = function(id) {
            NotificationStep.get({id : id}, function(result) {
                $scope.notificationStep = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:notificationStepUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.notificationStep.id != null) {
                NotificationStep.update($scope.notificationStep, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.notificationStep.created');
            } else {
                NotificationStep.save($scope.notificationStep, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.notificationStep.updated');
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
