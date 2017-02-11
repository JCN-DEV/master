'use strict';

angular.module('stepApp').controller('InstituteGovernBodyDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstituteGovernBody', 'Institute',
        function($scope, $stateParams, $modalInstance, entity, InstituteGovernBody, Institute) {

        $scope.instituteGovernBody = entity;
        $scope.institutes = Institute.query();
        $scope.load = function(id) {
            InstituteGovernBody.get({id : id}, function(result) {
                $scope.instituteGovernBody = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instituteGovernBodyUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instituteGovernBody.id != null) {
                InstituteGovernBody.update($scope.instituteGovernBody, onSaveSuccess, onSaveError);
            } else {
                InstituteGovernBody.save($scope.instituteGovernBody, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
