'use strict';

angular.module('stepApp')
    .controller('UmracRoleSetupController',
        ['$scope', 'UmracRoleSetup', 'UmracRoleSetupSearch', 'ParseLinks',
        function ($scope, UmracRoleSetup, UmracRoleSetupSearch, ParseLinks) {
        $scope.umracRoleSetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            UmracRoleSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.umracRoleSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            UmracRoleSetup.get({id: id}, function(result) {
                $scope.umracRoleSetup = result;
                $('#deleteUmracRoleSetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UmracRoleSetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUmracRoleSetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            UmracRoleSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.umracRoleSetups = result;
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
            $scope.umracRoleSetup = {
                roleId: null,
                roleName: null,
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
