'use strict';

angular.module('stepApp').controller('InstFinancialInfoTempDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstFinancialInfoTemp',
        function($scope, $stateParams, $modalInstance, entity, InstFinancialInfoTemp) {

        $scope.instFinancialInfoTemp = entity;
        $scope.load = function(id) {
            InstFinancialInfoTemp.get({id : id}, function(result) {
                $scope.instFinancialInfoTemp = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instFinancialInfoTempUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instFinancialInfoTemp.id != null) {
                InstFinancialInfoTemp.update($scope.instFinancialInfoTemp, onSaveSuccess, onSaveError);
            } else {
                InstFinancialInfoTemp.save($scope.instFinancialInfoTemp, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
