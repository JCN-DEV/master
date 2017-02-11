'use strict';

angular.module('stepApp')
    .controller('InstEmplPayscaleHistController',
    ['$scope','$state','$modal','InstEmplPayscaleHist','InstEmplPayscaleHistSearch','ParseLinks',
     function ($scope, $state, $modal, InstEmplPayscaleHist, InstEmplPayscaleHistSearch, ParseLinks) {

        $scope.instEmplPayscaleHists = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstEmplPayscaleHist.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instEmplPayscaleHists = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstEmplPayscaleHistSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instEmplPayscaleHists = result;
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
            $scope.instEmplPayscaleHist = {
                activationDate: null,
                endDate: null,
                id: null
            };
        };
    }]);
