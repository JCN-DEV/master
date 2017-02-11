'use strict';

angular.module('stepApp').controller('InstEmplPayscaleHistDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InstEmplPayscaleHist', 'InstEmployee', 'PayScale',
        function($scope, $stateParams, $modalInstance, entity, InstEmplPayscaleHist, InstEmployee, PayScale) {

        $scope.instEmplPayscaleHist = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.payscales = PayScale.query();
        $scope.load = function(id) {
            InstEmplPayscaleHist.get({id : id}, function(result) {
                $scope.instEmplPayscaleHist = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmplPayscaleHistUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instEmplPayscaleHist.id != null) {
                InstEmplPayscaleHist.update($scope.instEmplPayscaleHist, onSaveSuccess, onSaveError);
            } else {
                InstEmplPayscaleHist.save($scope.instEmplPayscaleHist, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
