'use strict';

angular.module('stepApp')
    .controller('InstituteInfraInfoController',
    ['$scope', '$state', '$modal', 'InstituteInfraInfo', 'InstituteInfraInfoSearch', 'ParseLinks',
    function ($scope, $state, $modal, InstituteInfraInfo, InstituteInfraInfoSearch, ParseLinks) {

        $scope.instituteInfraInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstituteInfraInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instituteInfraInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstituteInfraInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instituteInfraInfos = result;
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
            $scope.instituteInfraInfo = {
                status: null,
                id: null
            };
        };
    }]);
