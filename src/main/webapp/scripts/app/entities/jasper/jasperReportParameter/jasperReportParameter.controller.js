'use strict';

angular.module('stepApp')
    .controller('JasperReportParameterController',
    ['$scope', 'JasperReportParameter', 'JasperReportParameterSearch', 'ParseLinks',
    function ($scope, JasperReportParameter, JasperReportParameterSearch, ParseLinks) {
        $scope.jasperReportParameters = [];
        $scope.page = 0;
        $scope.loadAll = function () {
            JasperReportParameter.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jasperReportParameters = result;
            });
        };
        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            console.log("Delete ID : id");
            JasperReportParameter.get({id: id}, function (result) {
                $scope.jasperReportParameter = result;
                $('#deleteJasperReportParameterConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            JasperReportParameter.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteJasperReportParameterConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            JasperReportParameterSearch.query({query: $scope.searchQuery}, function (result) {
                $scope.jasperReportParameters = result;
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
            $scope.jasperReportParameter = {
                name: null,
                type: null,
                flevel: null,
                datatype: null,
                combodisplayfield: null,
                position: null,
                servicename: null,
                validationexp: null,
                maxlength: null,
                minlength: null,
                actionname: null,
                actiontype: null,
                id: null
            };
        };
    }]);
