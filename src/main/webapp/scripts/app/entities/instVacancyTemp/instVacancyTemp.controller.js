'use strict';

angular.module('stepApp')
    .controller('InstVacancyTempController', function ($scope, InstVacancyTemp, InstVacancyTempSearch, ParseLinks) {
        $scope.instVacancyTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstVacancyTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instVacancyTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            InstVacancyTemp.get({id: id}, function(result) {
                $scope.instVacancyTemp = result;
                $('#deleteInstVacancyTempConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            InstVacancyTemp.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteInstVacancyTempConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            InstVacancyTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instVacancyTemps = result;
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
            $scope.instVacancyTemp = {
                dateCreated: null,
                dateModified: null,
                status: null,
                empType: null,
                totalVacancy: null,
                filledUpVacancy: null,
                id: null
            };
        };
    });
