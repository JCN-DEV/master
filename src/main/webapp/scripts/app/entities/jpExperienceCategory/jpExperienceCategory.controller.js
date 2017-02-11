'use strict';

angular.module('stepApp')
    .controller('JpExperienceCategoryController',
    ['$scope', 'JpExperienceCategory', 'JpExperienceCategorySearch', 'ParseLinks',
    function ($scope, JpExperienceCategory, JpExperienceCategorySearch, ParseLinks) {
        $scope.jpExperienceCategorys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            JpExperienceCategory.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jpExperienceCategorys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            JpExperienceCategory.get({id: id}, function(result) {
                $scope.jpExperienceCategory = result;
                $('#deleteJpExperienceCategoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            JpExperienceCategory.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteJpExperienceCategoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            JpExperienceCategorySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.jpExperienceCategorys = result;
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
            $scope.jpExperienceCategory = {
                name: null,
                description: null,
                id: null
            };
        };
    }]);
