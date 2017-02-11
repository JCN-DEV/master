'use strict';

angular.module('stepApp')
    .controller('InstEmplWithhelHistController',
    ['$scope', '$state', '$modal', 'InstEmplWithhelHist', 'InstEmplWithhelHistSearch', 'ParseLinks',
    function ($scope, $state, $modal, InstEmplWithhelHist, InstEmplWithhelHistSearch, ParseLinks) {

        $scope.instEmplWithhelHists = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstEmplWithhelHist.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instEmplWithhelHists = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstEmplWithhelHistSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instEmplWithhelHists = result;
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
            $scope.instEmplWithhelHist = {
                withheldAmount: null,
                startDate: null,
                stopDate: null,
                createdDate: null,
                modifiedDate: null,
                status: null,
                remark: null,
                id: null
            };
        };
    }]);
