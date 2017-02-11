'use strict';

angular.module('stepApp')
    .controller('InstEmpCountController',
    ['$scope', '$state', '$modal', 'InstEmpCount', 'InstEmpCountSearch', 'ParseLinks',
    function ($scope, $state, $modal, InstEmpCount, InstEmpCountSearch, ParseLinks) {

        $scope.instEmpCounts = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstEmpCount.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instEmpCounts = result;
                if($scope.instEmpCounts.length==0){
                    $state.go('instGenInfo.instEmpCount.new');
                }
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstEmpCountSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instEmpCounts = result;
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
            $scope.instEmpCount = {
                totalMaleTeacher: null,
                totalFemaleTeacher: null,
                totalMaleEmployee: null,
                totalFemaleEmployee: null,
                totalGranted: null,
                totalEngaged: null,
                status: null,
                id: null
            };
        };
    }]);
