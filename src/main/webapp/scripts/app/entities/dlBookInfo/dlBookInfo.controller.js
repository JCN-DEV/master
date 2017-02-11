'use strict';

angular.module('stepApp')
    .controller('DlBookInfoController',
    ['$scope', '$state', '$modal', 'DlBookInfo', 'DlBookInfoSearch', 'ParseLinks',
    function ($scope, $state, $modal, DlBookInfo, DlBookInfoSearch, ParseLinks) {

        $scope.dlBookInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            DlBookInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dlBookInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            DlBookInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.dlBookInfos = result;
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
            $scope.dlBookInfo = {
                title: null,
                edition: null,
                isbnNo: null,
                authorName: null,
                copyright: null,
                publisherName: null,
                libraryName: null,
                callNo: null,
                totalCopies: null,
                purchaseDate: null,
                createdDate: null,
                updatedDate: null,
                createdBy: null,
                updatedBy: null,
                status: null,
                id: null
            };
        };
    }]);
