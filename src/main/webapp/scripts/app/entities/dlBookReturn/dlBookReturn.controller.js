'use strict';

angular.module('stepApp')
    .controller('DlBookReturnController',
    ['$scope', '$state', '$modal', 'DlBookReturn', 'DlBookReturnSearch', 'ParseLinks',
     function ($scope, $state, $modal, DlBookReturn, DlBookReturnSearch, ParseLinks) {

        $scope.dlBookReturns = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            DlBookReturn.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dlBookReturns = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            DlBookReturnSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.dlBookReturns = result;
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
            $scope.dlBookReturn = {
                issueId: null,
                createdDate: null,
                updatedDate: null,
                createdBy: null,
                updatedBy: null,
                status: null,
                id: null
            };
        };
    }]);
