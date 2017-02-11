'use strict';

angular.module('stepApp')
    .controller('CmsSubAssignController',
        ['$scope', '$state', '$modal', 'CmsSubAssign', 'CmsSubAssignSearch', 'ParseLinks',
        function ($scope, $state, $modal, CmsSubAssign, CmsSubAssignSearch, ParseLinks) {

        $scope.cmsSubAssigns = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            CmsSubAssign.query({page: $scope.page, size: 2000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.cmsSubAssigns = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CmsSubAssignSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cmsSubAssigns = result;
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
            $scope.cmsSubAssign = {
                subject: null,
                description: null,
                examFee: null,
                status: null,
                id: null
            };
        };
    }]);
