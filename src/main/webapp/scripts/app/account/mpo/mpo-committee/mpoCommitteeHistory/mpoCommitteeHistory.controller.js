'use strict';

angular.module('stepApp')
    .controller('MpoCommitteeHistoryController',
    ['$scope', '$state', '$modal', 'MpoCommitteeHistory', 'MpoCommitteeHistorySearch', 'ParseLinks',
    function ($scope, $state, $modal, MpoCommitteeHistory, MpoCommitteeHistorySearch, ParseLinks) {

        $scope.mpoCommitteeHistorys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            MpoCommitteeHistory.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.mpoCommitteeHistorys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            MpoCommitteeHistorySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.mpoCommitteeHistorys = result;
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
            $scope.mpoCommitteeHistory = {
                month: null,
                year: null,
                dateCrated: null,
                dateModified: null,
                status: null,
                id: null
            };
        };
    }]);
