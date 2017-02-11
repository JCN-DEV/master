'use strict';

angular.module('stepApp')
    .controller('MpoVacancyRoleDesgnationsController', function ($scope, $state, $modal, MpoVacancyRoleDesgnations, MpoVacancyRoleDesgnationsSearch, ParseLinks) {
      
        $scope.mpoVacancyRoleDesgnationss = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            MpoVacancyRoleDesgnations.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.mpoVacancyRoleDesgnationss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            MpoVacancyRoleDesgnationsSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.mpoVacancyRoleDesgnationss = result;
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
            $scope.mpoVacancyRoleDesgnations = {
                crateDate: null,
                updateDate: null,
                status: null,
                totalPost: null,
                id: null
            };
        };
    });
