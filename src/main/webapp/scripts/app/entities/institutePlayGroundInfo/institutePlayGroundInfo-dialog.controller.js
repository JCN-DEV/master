'use strict';

angular.module('stepApp').controller('InstitutePlayGroundInfoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstitutePlayGroundInfo', 'InstituteInfraInfo',
        function($scope, $stateParams, $modalInstance, entity, InstitutePlayGroundInfo, InstituteInfraInfo) {

        $scope.institutePlayGroundInfo = entity;
        $scope.instituteinfrainfos = InstituteInfraInfo.query();
        $scope.load = function(id) {
            InstitutePlayGroundInfo.get({id : id}, function(result) {
                $scope.institutePlayGroundInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:institutePlayGroundInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.institutePlayGroundInfo.id != null) {
                InstitutePlayGroundInfo.update($scope.institutePlayGroundInfo, onSaveSuccess, onSaveError);
            } else {
                InstitutePlayGroundInfo.save($scope.institutePlayGroundInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
