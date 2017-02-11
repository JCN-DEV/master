'use strict';

angular.module('stepApp').controller('CmsCurriculumController',
    ['$scope', '$state', '$modal', 'CmsCurriculumQuery', 'CmsCurriculum', 'CmsCurriculumSearch', 'ParseLinks',
        function ($scope, $state, $modal, CmsCurriculumQuery,CmsCurriculum, CmsCurriculumSearch, ParseLinks) {

        $scope.cmsCurriculums = [];
        $scope.tableParams = '';
        $scope.page = 0;
        $scope.currentPage = 1;
        $scope.pageSize = 10;
        $scope.loadAll = function() {
        CmsCurriculumQuery.query({page: $scope.page, size: 2000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.cmsCurriculums = result;
                //$scope.tableParams = new NgTableParams({}, { dataset: $scope.cmsCurriculums});
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
