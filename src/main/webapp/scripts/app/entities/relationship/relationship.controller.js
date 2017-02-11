'use strict';

angular.module('stepApp')
    .controller('RelationshipController', function ($scope, Relationship, RelationshipSearch, ParseLinks) {
        $scope.relationships = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Relationship.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.relationships = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Relationship.get({id: id}, function(result) {
                $scope.relationship = result;
                $('#deleteRelationshipConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Relationship.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteRelationshipConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            RelationshipSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.relationships = result;
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
            $scope.relationship = {
                name: null,
                description: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
