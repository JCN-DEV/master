'use strict';

angular.module('stepApp')
    .controller('JpLanguageProficiencyController',
    ['$scope', 'JpLanguageProficiency', 'JpLanguageProficiencySearch', 'ParseLinks',
    function ($scope, JpLanguageProficiency, JpLanguageProficiencySearch, ParseLinks) {
        $scope.jpLanguageProficiencys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            JpLanguageProficiency.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jpLanguageProficiencys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            JpLanguageProficiency.get({id: id}, function(result) {
                $scope.jpLanguageProficiency = result;
                $('#deleteJpLanguageProficiencyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            JpLanguageProficiency.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteJpLanguageProficiencyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            JpLanguageProficiencySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.jpLanguageProficiencys = result;
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
            $scope.jpLanguageProficiency = {
                name: null,
                reading: null,
                writing: null,
                speaking: null,
                listening: null,
                id: null
            };
        };
    }]);
