'use strict';

angular.module('stepApp')
    .controller('TempInstGenInfoController',
    ['$scope', '$state', '$modal', 'TempInstGenInfo', 'TempInstGenInfoSearch', 'ParseLinks',
    function ($scope, $state, $modal, TempInstGenInfo, TempInstGenInfoSearch, ParseLinks) {

        $scope.tempInstGenInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TempInstGenInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.tempInstGenInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            TempInstGenInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.tempInstGenInfos = result;
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
            $scope.tempInstGenInfo = {
                name: null,
                type: null,
                village: null,
                postOffice: null,
                postCode: null,
                landPhone: null,
                mobileNo: null,
                email: null,
                consArea: null,
                update: null,
                id: null
            };
        };
    }]);
