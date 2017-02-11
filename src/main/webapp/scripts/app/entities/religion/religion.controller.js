'use strict';

angular.module('stepApp')
    .controller('ReligionController',
    ['$scope', 'Religion', 'ReligionSearch', 'ParseLinks',

    function ($scope, Religion, ReligionSearch, ParseLinks) {
        $scope.religions = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Religion.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.religions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Religion.get({id: id}, function(result) {
                $scope.religion = result;
                $('#deleteReligionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Religion.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteReligionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            ReligionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.religions = result;
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
            $scope.religion = {
                name: null,
                description: null,
                id: null
            };
        };
    }]);
