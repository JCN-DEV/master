'use strict';

angular.module('stepApp').controller('MpoCommitteeHistoryDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'MpoCommitteeHistory', 'MpoCommitteePersonInfo',
        function($scope, $stateParams, $modalInstance, entity, MpoCommitteeHistory, MpoCommitteePersonInfo) {

        $scope.mpoCommitteeHistory = entity;
        $scope.mpocommitteepersoninfos = MpoCommitteePersonInfo.query();
        $scope.load = function(id) {
            MpoCommitteeHistory.get({id : id}, function(result) {
                $scope.mpoCommitteeHistory = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:mpoCommitteeHistoryUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.mpoCommitteeHistory.id != null) {
                MpoCommitteeHistory.update($scope.mpoCommitteeHistory, onSaveSuccess, onSaveError);
            } else {
                MpoCommitteeHistory.save($scope.mpoCommitteeHistory, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
