'use strict';

angular.module('stepApp')
    .controller('InstBuildingController',
    ['$scope','$state','$modal','InstBuilding','InstBuildingSearch','ParseLinks',
    function ($scope, $state, $modal, InstBuilding, InstBuildingSearch, ParseLinks) {

        $scope.instBuildings = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstBuilding.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instBuildings = result;

                if($scope.instBuildings.length==0){
                    $state.go('instGenInfo.infrastructureInfo.new');
                }
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstBuildingSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instBuildings = result;
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
            $scope.instBuilding = {
                totalArea: null,
                totalRoom: null,
                classRoom: null,
                officeRoom: null,
                otherRoom: null,
                id: null
            };
        };
    }]);
