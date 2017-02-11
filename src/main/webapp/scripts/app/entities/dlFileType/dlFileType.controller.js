'use strict';

angular.module('stepApp')
    .controller('DlFileTypeController',
    ['$scope', '$state', '$modal', 'DlFileType', 'DlFileTypeSearch', 'ParseLinks',
     function ($scope, $state, $modal, DlFileType, DlFileTypeSearch, ParseLinks) {

        $scope.dlFileTypes = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            DlFileType.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dlFileTypes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            DlFileTypeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.dlFileTypes = result;
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
            $scope.dlFileType = {
                fileType: null,
                limitMb: null,
                createdDate: null,
                updatedDate: null,
                createdBy: null,
                updatedBy: null,
                status: null,
                id: null
            };
        };
    }]);
