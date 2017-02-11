'use strict';

angular.module('stepApp')
    .controller('AlmHolidayController',
    ['$scope', 'AlmHoliday', 'AlmHolidaySearch', 'ParseLinks',
    function ($scope, AlmHoliday, AlmHolidaySearch, ParseLinks) {
        $scope.almHolidays = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AlmHoliday.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almHolidays = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AlmHoliday.get({id: id}, function(result) {
                $scope.almHoliday = result;
                $('#deleteAlmHolidayConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmHoliday.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmHolidayConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmHolidaySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almHolidays = result;
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
            $scope.almHoliday = {
                typeName: null,
                religion: null,
                occasion: null,
                fromDate: null,
                toDate: null,
                activeStatus: null

            };
        };
    }]);
