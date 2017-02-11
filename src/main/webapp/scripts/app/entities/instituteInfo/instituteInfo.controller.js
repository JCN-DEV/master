'use strict';

angular.module('stepApp')
    .controller('InstituteInfoController',
    ['$scope', '$state', '$modal', 'InstituteInfo', 'InstituteInfoSearch', 'ParseLinks',
    function ($scope, $state, $modal, InstituteInfo, InstituteInfoSearch, ParseLinks) {

        $scope.instituteInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstituteInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instituteInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstituteInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instituteInfos = result;
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
            $scope.instituteInfo = {
                code: null,
                publicationDate: null,
                id: null
            };
        };
    }]);
