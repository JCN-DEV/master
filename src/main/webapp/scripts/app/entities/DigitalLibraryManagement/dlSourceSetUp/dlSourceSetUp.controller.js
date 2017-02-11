'use strict';

angular.module('stepApp')
    .controller('DlSourceSetUpController', function ($scope, $state, $modal, DlSourceSetUp, DlSourceSetUpSearch, ParseLinks) {

        $scope.dlSourceSetUps = [];
        $scope.page = 0;
        $scope.currentPage = 1;
        $scope.pageSize = 10;
        $scope.loadAll = function() {
            DlSourceSetUp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dlSourceSetUps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            DlSourceSetUpSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.dlSourceSetUps = result;
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
            $scope.dlSourceSetUp = {
                name: null,
                fine: null,
                status: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
