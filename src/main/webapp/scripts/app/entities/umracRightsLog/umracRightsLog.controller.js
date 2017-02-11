'use strict';

angular.module('stepApp')
    .controller('UmracRightsLogController',
        ['$scope', 'UmracRightsLog', 'UmracRightsLogSearch', 'ParseLinks',
        function ($scope, UmracRightsLog, UmracRightsLogSearch, ParseLinks) {
        $scope.umracRightsLogs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            UmracRightsLog.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.umracRightsLogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            UmracRightsLog.get({id: id}, function(result) {
                $scope.umracRightsLog = result;
                $('#deleteUmracRightsLogConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UmracRightsLog.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUmracRightsLogConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            UmracRightsLogSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.umracRightsLogs = result;
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
            $scope.umracRightsLog = {
                rightId: null,
                roleId: null,
                module_id: null,
                subModule_id: null,
                rights: null,
                description: null,
                status: null,
                changeDate: null,
                changeBy: null,
                updatedBy: null,
                updatedTime: null,
                id: null
            };
        };
    }]);
