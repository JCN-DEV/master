'use strict';

angular.module('stepApp')
    .controller('CmsCurriculumController',
    ['$scope', '$state', '$modal', 'CmsCurriculum', 'CmsCurriculumSearch', 'ParseLinks',
     function ($scope, $state, $modal, CmsCurriculum, CmsCurriculumSearch, ParseLinks) {

        $scope.cmsCurriculums = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            CmsCurriculum.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.cmsCurriculums = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CmsCurriculumSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cmsCurriculums = result;
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
            $scope.cmsCurriculum = {
                code: null,
                name: null,
                duration: null,
                description: null,
                status: null,
                id: null
            };
        };
    }]);
