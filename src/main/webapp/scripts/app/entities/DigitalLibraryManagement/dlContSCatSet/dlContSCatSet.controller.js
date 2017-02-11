'use strict';

angular.module('stepApp')
    .controller('DlContSCatSetController',
    ['$scope', '$state', '$modal', 'DlContSCatSet', 'DlContSCatSetSearch', 'ParseLinks',
     function ($scope, $state, $modal, DlContSCatSet, DlContSCatSetSearch, ParseLinks) {

        $scope.dlContSCatSets = [];
        $scope.page = 0;
        $scope.currentPage = 1;
                 $scope.pageSize = 10;
        $scope.loadAll = function() {
            DlContSCatSet.query({page: $scope.page, size: 1000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dlContSCatSets = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            DlContSCatSetSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.dlContSCatSets = result;
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
            $scope.dlContSCatSet = {
                code: null,
                name: null,
                description: null,
                pStatus: null,
                createdDate: null,
                updatedDate: null,
                createdBy: null,
                updatedBy: null,
                status: null,
                id: null
            };
        };
    }]);
