'use strict';

angular.module('stepApp')
    .controller('InstGovBodyAccessController', function ($scope, $state, $modal, InstGovBodyAccess, InstGovBodyAccessSearch, ParseLinks, InstGovBodyAccessCurrentInstitute) {

        $scope.instGovBodyAccesss = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstGovBodyAccessCurrentInstitute.get({}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instGovBodyAccesss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstGovBodyAccessSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instGovBodyAccesss = result;
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
            $scope.instGovBodyAccess = {
                dateCreated: null,
                dateModified: null,
                status: null,
                id: null
            };
        };
    });
