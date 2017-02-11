'use strict';

angular.module('stepApp')
    .controller('DteJasperConfigurationController', function ($scope, DteJasperConfiguration, DteJasperConfigurationSearch, ParseLinks) {
        $scope.dteJasperConfigurations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            DteJasperConfiguration.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dteJasperConfigurations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            DteJasperConfiguration.get({id: id}, function(result) {
                $scope.dteJasperConfiguration = result;
                $('#deleteDteJasperConfigurationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            DteJasperConfiguration.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDteJasperConfigurationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            DteJasperConfigurationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.dteJasperConfigurations = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.dteJasperConfiguration = {
                domain: null,
                port: null,
                username: null,
                password: null,
                createUrl: null,
                createDate: null,
                modifiedDate: null,
                createBy: null,
                modifiedBy: null,
                status: null,
                id: null
            };
        };
    });
