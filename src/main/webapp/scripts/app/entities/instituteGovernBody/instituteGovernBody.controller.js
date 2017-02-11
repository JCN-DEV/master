'use strict';

angular.module('stepApp')
    .controller('InstituteGovernBodyController',
    ['$scope', '$state', '$modal', 'InstituteGovernBody', 'InstituteGovernBodySearch', 'ParseLinks',
    function ($scope, $state, $modal, InstituteGovernBody, InstituteGovernBodySearch, ParseLinks) {

        $scope.instituteGovernBodys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstituteGovernBody.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instituteGovernBodys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstituteGovernBodySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instituteGovernBodys = result;
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
            $scope.instituteGovernBody = {
                name: null,
                position: null,
                mobileNo: null,
                status: null,
                id: null
            };
        };
    }]);
