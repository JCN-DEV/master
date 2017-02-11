'use strict';

angular.module('stepApp')
    .controller('MpoVacancyRoleController', function ($scope, $state, $modal, MpoVacancyRole, MpoVacancyRoleSearch, ParseLinks) {
      
        $scope.mpoVacancyRoles = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            MpoVacancyRole.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.mpoVacancyRoles = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            MpoVacancyRoleSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.mpoVacancyRoles = result;
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
            $scope.mpoVacancyRole = {
                crateDate: null,
                updateDate: null,
                status: null,
                totalTrade: null,
                vacancyRoleType: null,
                totalVacancy: null,
                id: null
            };
        };
    });
