'use strict';

angular.module('stepApp')
    .controller('JasperReportController',
    ['$scope',  'JasperReport', 'JasperReportSearch', 'ParseLinks',
    function ($scope, JasperReport, JasperReportSearch, ParseLinks) {
        $scope.jasperReports = [];
        $scope.page = 0;
        $scope.loadAll = function () {
            JasperReport.query({page: $scope.page, size: 30}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jasperReports = result;
            });
        };


        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            JasperReport.get({id: id}, function (result) {
                $scope.jasperReport = result;
                $('#deleteJasperReportConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            JasperReport.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteJasperReportConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            JasperReportSearch.query({query: $scope.searchQuery}, function (result) {
                $scope.jasperReports = result;
            }, function (response) {
                if (response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.jasperReport = {
                name: null,
                path: null,
                module: null,
                role: null,
                storeprocedurestatus: null,
                procedurename: null,
                status: null,
                createdate: null,
                id: null
            };
        };
    }]);
