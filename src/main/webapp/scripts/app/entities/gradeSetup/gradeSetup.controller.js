'use strict';

angular.module('stepApp')
    .controller('GradeSetupController',
    ['$scope','$state','$modal','GradeSetup','GradeSetupSearch','ParseLinks',
    function ($scope, $state, $modal, GradeSetup, GradeSetupSearch, ParseLinks) {

        $scope.gradeSetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            GradeSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.gradeSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            GradeSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.gradeSetups = result;
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
            $scope.gradeSetup = {
                no: null,
                details: null,
                gradeClass: null,
                type: null,
                status: null,
                id: null
            };
        };
    }]);
