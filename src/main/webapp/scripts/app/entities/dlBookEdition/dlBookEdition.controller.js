'use strict';

angular.module('stepApp')
    .controller('DlBookEditionController', function ($scope, $state, $modal, DlBookEdition, DlBookEditionSearch, ParseLinks) {
      
        $scope.dlBookEditions = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            DlBookEdition.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dlBookEditions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            DlBookEditionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.dlBookEditions = result;
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
            $scope.dlBookEdition = {
                edition: null,
                totalCopies: null,
                compensation: null,
                createDate: null,
                updateDate: null,
                createBy: null,
                updateBy: null,
                status: null,
                id: null
            };
        };
    });
