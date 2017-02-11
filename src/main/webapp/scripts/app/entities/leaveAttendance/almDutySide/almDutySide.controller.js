'use strict';

angular.module('stepApp')
    .controller('AlmDutySideController',
   ['$scope', 'AlmDutySide', 'AlmDutySideSearch', 'ParseLinks',
    function ($scope, AlmDutySide, AlmDutySideSearch, ParseLinks) {
        $scope.almDutySides = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AlmDutySide.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almDutySides = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AlmDutySide.get({id: id}, function(result) {
                $scope.almDutySide = result;
                $('#deleteAlmDutySideConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmDutySide.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmDutySideConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmDutySideSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almDutySides = result;
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
            $scope.almDutySide = {
                sideName: null,
                description: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
