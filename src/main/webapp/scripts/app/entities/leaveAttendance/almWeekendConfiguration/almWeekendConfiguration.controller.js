'use strict';

angular.module('stepApp')
    .controller('AlmWeekendConfigurationController',
    ['$scope', 'AlmWeekendConfiguration', 'AlmWeekendConfigurationSearch', 'ParseLinks',
    function ($scope, AlmWeekendConfiguration, AlmWeekendConfigurationSearch, ParseLinks) {
        $scope.almWeekendConfigurations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AlmWeekendConfiguration.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almWeekendConfigurations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AlmWeekendConfiguration.get({id: id}, function(result) {
                $scope.almWeekendConfiguration = result;
                $('#deleteAlmWeekendConfigurationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmWeekendConfiguration.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmWeekendConfigurationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmWeekendConfigurationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almWeekendConfigurations = result;
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
            $scope.almWeekendConfiguration = {
                dayName: null,
                isHalfDay: null,
                halfDay: null,
                description: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
