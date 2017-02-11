'use strict';

angular.module('stepApp').controller('InstituteLabInfoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstituteLabInfo', 'InstituteInfraInfo',
        function($scope, $stateParams, $modalInstance, entity, InstituteLabInfo, InstituteInfraInfo) {

        $scope.instituteLabInfo = entity;
        $scope.instituteinfrainfos = InstituteInfraInfo.query();
        $scope.load = function(id) {
            InstituteLabInfo.get({id : id}, function(result) {
                $scope.instituteLabInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instituteLabInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instituteLabInfo.id != null) {
                InstituteLabInfo.update($scope.instituteLabInfo, onSaveSuccess, onSaveError);
            } else {
                InstituteLabInfo.save($scope.instituteLabInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
