'use strict';

angular.module('stepApp').controller('InstituteBuildingDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstituteBuilding',
        function($scope, $stateParams, $modalInstance, entity, InstituteBuilding) {

        $scope.instituteBuilding = entity;
        $scope.load = function(id) {
            InstituteBuilding.get({id : id}, function(result) {
                $scope.instituteBuilding = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instituteBuildingUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instituteBuilding.id != null) {
                InstituteBuilding.update($scope.instituteBuilding, onSaveSuccess, onSaveError);
            } else {
                InstituteBuilding.save($scope.instituteBuilding, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
