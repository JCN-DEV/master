'use strict';

angular.module('stepApp')
    .controller('InstitutePlayGroundInfoController',
    ['$scope', '$state', '$modal', 'InstitutePlayGroundInfo', 'InstitutePlayGroundInfoSearch', 'ParseLinks',
    function ($scope, $state, $modal, InstitutePlayGroundInfo, InstitutePlayGroundInfoSearch, ParseLinks) {

        $scope.institutePlayGroundInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstitutePlayGroundInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.institutePlayGroundInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstitutePlayGroundInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.institutePlayGroundInfos = result;
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
            $scope.institutePlayGroundInfo = {
                playgroundNo: null,
                area: null,
                id: null
            };
        };
    }]);
