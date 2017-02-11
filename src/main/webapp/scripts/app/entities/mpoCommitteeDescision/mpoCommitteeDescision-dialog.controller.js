'use strict';

angular.module('stepApp').controller('MpoCommitteeDescisionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'MpoCommitteeDescision', 'MpoCommitteePersonInfo', 'MpoApplication',
        function($scope, $stateParams, $modalInstance, entity, MpoCommitteeDescision, MpoCommitteePersonInfo, MpoApplication) {

        $scope.mpoCommitteeDescision = entity;
        $scope.mpocommitteepersoninfos = MpoCommitteePersonInfo.query();
        $scope.mpoapplications = MpoApplication.query();
        $scope.load = function(id) {
            MpoCommitteeDescision.get({id : id}, function(result) {
                $scope.mpoCommitteeDescision = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:mpoCommitteeDescisionUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.mpoCommitteeDescision.id != null) {
                MpoCommitteeDescision.update($scope.mpoCommitteeDescision, onSaveSuccess, onSaveError);
            } else {
                MpoCommitteeDescision.save($scope.mpoCommitteeDescision, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
