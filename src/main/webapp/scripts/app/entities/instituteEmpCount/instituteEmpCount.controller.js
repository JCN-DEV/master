'use strict';

angular.module('stepApp')
    .controller('InstituteEmpCountController',
    ['$scope','$state','$modal','InstituteEmpCount','InstituteEmpCountSearch','ParseLinks',
    function ($scope, $state, $modal, InstituteEmpCount, InstituteEmpCountSearch, ParseLinks) {

        $scope.instituteEmpCounts = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstituteEmpCount.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instituteEmpCounts = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstituteEmpCountSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instituteEmpCounts = result;
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
            $scope.instituteEmpCount = {
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
