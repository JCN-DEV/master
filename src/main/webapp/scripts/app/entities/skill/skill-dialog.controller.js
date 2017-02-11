'use strict';

angular.module('stepApp').controller('SkillDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Skill', 'Employee', 'User',
        function($scope, $stateParams, $modalInstance, entity, Skill, Employee, User) {

        $scope.skill = entity;
        $scope.employees = Employee.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            Skill.get({id : id}, function(result) {
                $scope.skill = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:skillUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.skill.id != null) {
                Skill.update($scope.skill, onSaveSuccess, onSaveError);
            } else {
                Skill.save($scope.skill, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
