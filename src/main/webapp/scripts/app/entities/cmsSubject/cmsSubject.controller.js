'use strict';

angular.module('stepApp')
    .controller('CmsSubjectController',
    ['$scope', '$state', '$modal', 'CmsSubject', 'CmsSubjectSearch', 'ParseLinks',
     function ($scope, $state, $modal, CmsSubject, CmsSubjectSearch, ParseLinks) {

        $scope.cmsSubjects = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            CmsSubject.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.cmsSubjects = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CmsSubjectSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cmsSubjects = result;
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
            $scope.cmsSubject = {
                code: null,
                name: null,
                theoryCredHr: null,
                pracCredHr: null,
                totalCredHr: null,
                theoryCon: null,
                theoryFinal: null,
                pracCon: null,
                pracFinal: null,
                totalMarks: null,
                description: null,
                status: null,
                id: null
            };
        };
    }]);
