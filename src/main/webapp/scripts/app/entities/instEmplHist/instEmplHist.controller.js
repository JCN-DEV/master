'use strict';

angular.module('stepApp')
    .controller('InstEmplHistController',
    ['$scope','$state','$modal','DataUtils','InstEmplHist','InstEmplHistSearch','ParseLinks',
    function ($scope, $state, $modal, DataUtils, InstEmplHist, InstEmplHistSearch, ParseLinks) {

        $scope.instEmplHists = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstEmplHist.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instEmplHists = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstEmplHistSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instEmplHists = result;
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
            $scope.instEmplHist = {
                name: null,
                designation: null,
                jobArea: null,
                start: null,
                end: null,
                onTrack: null,
                telephone: null,
                ext: null,
                email: null,
                mobile: null,
                website: null,
                certificateCopy: null,
                certificateCopyContentType: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    }]);
