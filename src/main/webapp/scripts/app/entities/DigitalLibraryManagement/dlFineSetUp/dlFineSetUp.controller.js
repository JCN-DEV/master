'use strict';

angular.module('stepApp')
    .controller('DlFineSetUpController', function ($scope, $state, $modal, DlFineSetUp, DlFineSetUpSearch, ParseLinks) {

        $scope.dlFineSetUps = [];
        $scope.page = 0;
        $scope.currentPage = 1;
         $scope.pageSize = 10;
        $scope.loadAll = function() {
            DlFineSetUp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dlFineSetUps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            DlFineSetUpSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.dlFineSetUps = result;
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
            $scope.dlFineSetUp = {
                timeLimit: null,
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
