'use strict';

angular.module('stepApp').controller('LangDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Lang', 'Employee', 'User',
        function($scope, $stateParams, $modalInstance, entity, Lang, Employee, User) {

        $scope.lang = entity;
        $scope.employees = Employee.query();
        $scope.users = User.query();
        $scope.load = function(id) {
            Lang.get({id : id}, function(result) {
                $scope.lang = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:langUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.lang.id != null) {
                Lang.update($scope.lang, onSaveSuccess, onSaveError);
            } else {
                Lang.save($scope.lang, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
