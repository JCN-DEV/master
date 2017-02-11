'use strict';

angular.module('stepApp')
    .controller('InstituteBuildingController',
    ['$scope','$state','$modal','InstituteBuilding','InstituteBuildingSearch','ParseLinks',
    function ($scope, $state, $modal, InstituteBuilding, InstituteBuildingSearch, ParseLinks) {

        $scope.instituteBuildings = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstituteBuilding.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instituteBuildings = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstituteBuildingSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instituteBuildings = result;
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
            $scope.instituteBuilding = {
                totalArea: null,
                totalRoom: null,
                classRoom: null,
                officeRoom: null,
                otherRoom: null,
                id: null
            };
        };
    }]);
