'use strict';

angular.module('stepApp').controller('JasperReportsController',
    ['$scope', '$stateParams','$rootScope','GetReportsByModule',
    function ($scope, $stateParams,GetReportsByModule) {

        //$scope.jasperReport = entity;
        //$scope.load = function (id) {
        //    JasperReport.get({id: id}, function (result) {
        //        $scope.jasperReport = result;
        //    });
        //};

        $scope.jasperReports =[];
        var loadModule = function () {
            GetReportsByModule.query({module: $stateParams.module}, function (result) {
                    //$scope.jasperReports = result;
                    console.log(result);
                },
                function (response) {

                }
            );
        };

        loadModule();







    }]);

