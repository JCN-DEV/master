'use strict';

angular.module('stepApp')
    .controller('GazetteSetupController',
    ['$scope','GazetteSetup','GazetteSetupSearch','ParseLinks',
    function ($scope, GazetteSetup, GazetteSetupSearch, ParseLinks) {
        $scope.gazetteSetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            GazetteSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.gazetteSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            GazetteSetup.get({id: id}, function(result) {
                $scope.gazetteSetup = result;
                $('#deleteGazetteSetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            GazetteSetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteGazetteSetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            GazetteSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.gazetteSetups = result;
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

        $scope.calendar = {
            opened: {},
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };

        $scope.clear = function () {
            $scope.gazetteSetup = {
                name: null,
                status: null,
                createBy: null,
                createDate: null,
                updateBy: null,
                updateDate: null,
                remarks: null,
                id: null
            };
        };
    }]);
