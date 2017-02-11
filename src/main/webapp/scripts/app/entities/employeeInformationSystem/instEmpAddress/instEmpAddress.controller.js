'use strict';

angular.module('stepApp')
    .controller('InstEmpAddressController',
    ['$scope', '$state', '$modal', 'InstEmpAddress', 'InstEmpAddressSearch', 'ParseLinks',
     function ($scope, $state, $modal, InstEmpAddress, InstEmpAddressSearch, ParseLinks) {

        $scope.instEmpAddresss = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstEmpAddress.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instEmpAddresss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstEmpAddressSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instEmpAddresss = result;
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
            $scope.instEmpAddress = {
                address: null,
                status: null,
                id: null
            };
        };
    }]);
