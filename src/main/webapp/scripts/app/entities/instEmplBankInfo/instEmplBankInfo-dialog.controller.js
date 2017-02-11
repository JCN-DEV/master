'use strict';

angular.module('stepApp').controller('InstEmplBankInfoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstEmplBankInfo', 'InstEmployee',
        function($scope, $stateParams, $modalInstance, entity, InstEmplBankInfo, InstEmployee) {

        $scope.instEmplBankInfo = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.load = function(id) {
            InstEmplBankInfo.get({id : id}, function(result) {
                $scope.instEmplBankInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmplBankInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instEmplBankInfo.id != null) {
                InstEmplBankInfo.update($scope.instEmplBankInfo, onSaveSuccess, onSaveError);
            } else {
                InstEmplBankInfo.save($scope.instEmplBankInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
