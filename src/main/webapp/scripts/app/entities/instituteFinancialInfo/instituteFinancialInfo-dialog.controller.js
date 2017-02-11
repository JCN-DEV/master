'use strict';

angular.module('stepApp').controller('InstituteFinancialInfoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstituteFinancialInfo',
        function($scope, $stateParams, $modalInstance, entity, InstituteFinancialInfo) {

        $scope.instituteFinancialInfo = entity;
        $scope.load = function(id) {
            InstituteFinancialInfo.get({id : id}, function(result) {
                $scope.instituteFinancialInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instituteFinancialInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instituteFinancialInfo.id != null) {
                InstituteFinancialInfo.update($scope.instituteFinancialInfo, onSaveSuccess, onSaveError);
            } else {
                InstituteFinancialInfo.save($scope.instituteFinancialInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
