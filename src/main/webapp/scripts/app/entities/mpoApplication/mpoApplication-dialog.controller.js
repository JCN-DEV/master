'use strict';

angular.module('stepApp').controller('MpoApplicationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'MpoApplication', 'Employee', 'Institute',
        function($scope, $stateParams, $modalInstance, entity, MpoApplication, Employee, Institute) {

        $scope.mpoApplication = entity;
        $scope.employees = Employee.query();
        $scope.institutes = Institute.query();
        $scope.load = function(id) {
            MpoApplication.get({id : id}, function(result) {
                $scope.mpoApplication = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:mpoApplicationUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.mpoApplication.id != null) {
                MpoApplication.update($scope.mpoApplication, onSaveFinished);
            } else {
                MpoApplication.save($scope.mpoApplication, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
