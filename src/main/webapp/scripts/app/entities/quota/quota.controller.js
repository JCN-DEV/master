'use strict';

angular.module('stepApp')

    .controller('QuotaController',
    ['$scope','$state','$modal','DataUtils','Quota','QuotaSearch','ParseLinks',
    function ($scope, $state, $modal, DataUtils, Quota, QuotaSearch, ParseLinks) {

        $scope.quotas = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Quota.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.quotas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            QuotaSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.quotas = result;
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
            $scope.quota = {
                name: null,
                certificate: null,
                certificateContentType: null,
                description: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    }]);
