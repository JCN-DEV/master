'use strict';

angular.module('stepApp').controller('CareerInformationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CareerInformation', 'Employee', 'User',
        function($scope, $stateParams, $modalInstance, entity, CareerInformation, Employee, User) {

        $scope.careerInformation = entity;
        $scope.employees = Employee.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            CareerInformation.get({id : id}, function(result) {
                $scope.careerInformation = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:careerInformationUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.careerInformation.id != null) {
                CareerInformation.update($scope.careerInformation, onSaveSuccess, onSaveError);
            } else {
                CareerInformation.save($scope.careerInformation, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
