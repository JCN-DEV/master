'use strict';

angular.module('stepApp').controller('MpoCommitteePersonInfoDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'MpoCommitteePersonInfo', 'User',
        function($scope, $stateParams, $modalInstance, $q, entity, MpoCommitteePersonInfo, User) {

        $scope.mpoCommitteePersonInfo = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            MpoCommitteePersonInfo.get({id : id}, function(result) {
                $scope.mpoCommitteePersonInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:mpoCommitteePersonInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.mpoCommitteePersonInfo.id != null) {
                MpoCommitteePersonInfo.update($scope.mpoCommitteePersonInfo, onSaveSuccess, onSaveError);
            } else {
                MpoCommitteePersonInfo.save($scope.mpoCommitteePersonInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
