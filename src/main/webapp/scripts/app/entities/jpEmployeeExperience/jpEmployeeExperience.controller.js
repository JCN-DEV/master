'use strict';

angular.module('stepApp')
    .controller('JpEmployeeExperienceController',
     ['$scope', 'JpEmployeeExperience', 'JpEmployeeExperienceSearch', 'ParseLinks',
     function ($scope, JpEmployeeExperience, JpEmployeeExperienceSearch, ParseLinks) {
        $scope.jpEmployeeExperiences = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            JpEmployeeExperience.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jpEmployeeExperiences = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            JpEmployeeExperience.get({id: id}, function(result) {
                $scope.jpEmployeeExperience = result;
                $('#deleteJpEmployeeExperienceConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            JpEmployeeExperience.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteJpEmployeeExperienceConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            JpEmployeeExperienceSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.jpEmployeeExperiences = result;
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
            $scope.jpEmployeeExperience = {
                skill: null,
                skillDescription: null,
                id: null
            };
        };
    }]);
