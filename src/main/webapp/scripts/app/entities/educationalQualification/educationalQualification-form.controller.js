'use strict';

angular.module('stepApp').controller('EducationalQualificationFormController',
    ['$scope', '$stateParams',  'entity', 'EducationalQualification', 'Employee', 'User',
        function($scope, $stateParams, entity, EducationalQualification, Employee, User) {

        $scope.educationalQualification = entity;
        $scope.employees = Employee.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            EducationalQualification.get({id : id}, function(result) {
                $scope.educationalQualification = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:educationalQualificationUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.educationalQualification.id != null) {
                EducationalQualification.update($scope.educationalQualification, onSaveSuccess, onSaveError);
            } else {
                EducationalQualification.save($scope.educationalQualification, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {

        };
}]);
