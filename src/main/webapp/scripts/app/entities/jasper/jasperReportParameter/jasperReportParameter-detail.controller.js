'use strict';

angular.module('stepApp')
    .controller('JasperReportParameterDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'JasperReportParameter', 'JasperReport',
     function ($scope, $rootScope, $stateParams, entity, JasperReportParameter, JasperReport) {
        $scope.jasperReportParameter = entity;
        $scope.load = function (id) {
            JasperReportParameter.get({id: id}, function (result) {
                $scope.jasperReportParameter = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:jasperReportParameterUpdate', function (event, result) {
            $scope.jasperReportParameter = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
