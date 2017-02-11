'use strict';

angular.module('stepApp').controller('JasperReportParameterFormController',
    ['$scope', '$state', '$location', '$stateParams', 'entity', 'JasperReportParameter', 'JasperReport',
    function ($scope, $state, $location, $stateParams, entity, JasperReportParameter, JasperReport) {


        $scope.jasperReportParameter = entity;
        $scope.jasperreports = JasperReport.query({size: 500});


        $scope.load = function (id) {
            JasperReportParameter.get({id: id}, function (result) {
                $scope.jasperReportParameter = result;
            });
        };


        if ($scope.jasperReport != null) {
            $stateParams.jasperReport = $scope.jasperReport;

        }

        var onSaveFinished = function (result) {

            $scope.$emit('stepApp:jasperReportParameterUpdate', result.jasperReport.id);
            $scope.isSaving = false;
            //$state.go('jasperReport.detail({id:})');
            $location.path('/jasperReport/' + result.jasperReport.id);
        };


        $scope.save = function () {
            if ($scope.jasperReportParameter != null && $scope.jasperReportParameter.id != null) {
                $scope.jasperReportParameter.servicename=$scope.serviceName;
                JasperReportParameter.update($scope.jasperReportParameter, onSaveFinished);
            } else {
                $scope.jasperReportParameter.servicename=$scope.serviceName;
                JasperReportParameter.save($scope.jasperReportParameter, onSaveFinished);
            }

        };

        $scope.clear = function () {
            $modalInstance.dismiss('cancel');
            $location.path('/jasperReport/' + result.jasperReport.id);
            //$state.go('jasperReport.detail({id:'+ $stateParams.id+'})');
        };

    }]);

