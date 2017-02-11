'use strict';

angular.module('stepApp')
    .controller('InstituteLabInfoController',
    ['$scope', '$state', '$modal', 'InstituteLabInfo', 'InstituteLabInfoSearch', 'ParseLinks',
    function ($scope, $state, $modal, InstituteLabInfo, InstituteLabInfoSearch, ParseLinks) {

        $scope.instituteLabInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstituteLabInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instituteLabInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstituteLabInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instituteLabInfos = result;
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
            $scope.instituteLabInfo = {
                nameOrNumber: null,
                buildingNameOrNumber: null,
                length: null,
                width: null,
                totalBooks: null,
                id: null
            };
        };
    }]);
