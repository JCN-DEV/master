'use strict';

angular.module('stepApp')
    .controller('InstGovernBodyController',
    ['$scope','$state','$modal','InstGovernBody','InstGovernBodySearch','ParseLinks',
    function ($scope, $state, $modal, InstGovernBody, InstGovernBodySearch, ParseLinks) {

        $scope.instGovernBodys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstGovernBody.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instGovernBodys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstGovernBodySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instGovernBodys = result;
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
            $scope.instGovernBody = {
                name: null,
                position: null,
                mobileNo: null,
                status: null,
                id: null
            };
        };
    }]);
