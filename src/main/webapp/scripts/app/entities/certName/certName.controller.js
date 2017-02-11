'use strict';

angular.module('stepApp')
    .controller('CertNameController',
    ['$scope', '$state', '$modal', 'CertName', 'CertNameSearch', 'ParseLinks',
     function ($scope, $state, $modal, CertName, CertNameSearch, ParseLinks) {

        $scope.certNames = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            CertName.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.certNames = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CertNameSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.certNames = result;
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
            $scope.certName = {
                name: null,
                id: null
            };
        };
    }]);
