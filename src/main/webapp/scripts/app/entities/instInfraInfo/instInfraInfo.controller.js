'use strict';

angular.module('stepApp')
    .controller('InstInfraInfoController',
    ['$scope','$state','$modal','InstInfraInfo','InstInfraInfoSearch','ParseLinks',
    function ($scope, $state, $modal, InstInfraInfo, InstInfraInfoSearch, ParseLinks) {

        $scope.instInfraInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstInfraInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instInfraInfos = result;
                if($scope.instInfraInfos.length==0){
                    $state.go('instGenInfo.instInfraInfo.new');
                }
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstInfraInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instInfraInfos = result;
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
            $scope.instInfraInfo = {
                status: null,
                id: null
            };
        };
    }]);
