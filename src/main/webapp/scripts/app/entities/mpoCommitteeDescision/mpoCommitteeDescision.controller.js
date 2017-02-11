'use strict';

angular.module('stepApp')
    .controller('MpoCommitteeDescisionController',
    ['$scope','$state','$modal','MpoCommitteeDescision','MpoCommitteeDescisionSearch','ParseLinks',
    function ($scope, $state, $modal, MpoCommitteeDescision, MpoCommitteeDescisionSearch, ParseLinks) {

        $scope.mpoCommitteeDescisions = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            MpoCommitteeDescision.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.mpoCommitteeDescisions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            MpoCommitteeDescisionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.mpoCommitteeDescisions = result;
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
            $scope.mpoCommitteeDescision = {
                comments: null,
                status: null,
                id: null
            };
        };
    }]);
