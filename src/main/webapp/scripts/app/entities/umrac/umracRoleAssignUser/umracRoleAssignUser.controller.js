'use strict';

angular.module('stepApp')
    .controller('UmracRoleAssignUserController',
    ['$scope', 'UmracRoleAssignUser', 'UmracRoleAssignUserSearch', 'ParseLinks',
    function ($scope, UmracRoleAssignUser, UmracRoleAssignUserSearch, ParseLinks) {
        $scope.umracRoleAssignUsers = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            UmracRoleAssignUser.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.umracRoleAssignUsers = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            UmracRoleAssignUser.get({id: id}, function(result) {
                $scope.umracRoleAssignUser = result;
                $('#deleteUmracRoleAssignUserConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UmracRoleAssignUser.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUmracRoleAssignUserConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            UmracRoleAssignUserSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.umracRoleAssignUsers = result;
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
            $scope.umracRoleAssignUser = {
                description: null,
                status: null,
                createDate: null,
                createBy: null,
                updatedBy: null,
                updatedTime: null,
                id: null
            };
        };
    }]);
