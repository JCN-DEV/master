'use strict';

angular.module('stepApp').controller('JasperReportDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'JasperReport',
    function ($scope, $stateParams, $modalInstance, entity, JasperReport) {

        $scope.jasperReport = entity;
        $scope.load = function (id) {
            JasperReport.get({id: id}, function (result) {
                $scope.jasperReport = result;
            });
        };

        $scope.storeProcedureClasssSelection = function (statusflag) {

        };


        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:jasperReportUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.jasperReport.id != null) {
                JasperReport.update($scope.jasperReport, onSaveFinished);
            } else {
                JasperReport.save($scope.jasperReport, onSaveFinished);
            }
        };


        $scope.clear = function () {
            $modalInstance.dismiss('cancel');
        };
    }]);

