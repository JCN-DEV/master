'use strict';

angular.module('stepApp').controller('JasperReportParameterDialogController',
    ['$scope', '$state', '$stateParams', '$modalInstance', 'entity', 'JasperReportParameter', 'JasperReport',
        function($scope, $state, $stateParams, $modalInstance, entity, JasperReportParameter, JasperReport) {

        $scope.jasperReportParameter = entity;
        $scope.jasperreports = JasperReport.query();

        $scope.load = function(id) {
            JasperReportParameter.get({id : id}, function(result) {
                $scope.jasperReportParameter = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:jasperReportParameterUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.jasperReportParameter.id != null) {
                JasperReportParameter.update($scope.jasperReportParameter, onSaveFinished);
            } else {
                JasperReportParameter.save($scope.jasperReportParameter, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
            $state.go('jasperReport.detail({id:'+ $stateParams.id+'})');
        };
}]);
