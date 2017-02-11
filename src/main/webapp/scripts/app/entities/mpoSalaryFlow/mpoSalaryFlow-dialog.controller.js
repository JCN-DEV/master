'use strict';

angular.module('stepApp').controller('MpoSalaryFlowDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'MpoSalaryFlow',
        function($scope, $stateParams, $modalInstance, entity, MpoSalaryFlow) {

        $scope.mpoSalaryFlow = entity;
        $scope.load = function(id) {
            MpoSalaryFlow.get({id : id}, function(result) {
                $scope.mpoSalaryFlow = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:mpoSalaryFlowUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.mpoSalaryFlow.id != null) {
                MpoSalaryFlow.update($scope.mpoSalaryFlow, onSaveFinished);
            } else {
                MpoSalaryFlow.save($scope.mpoSalaryFlow, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
