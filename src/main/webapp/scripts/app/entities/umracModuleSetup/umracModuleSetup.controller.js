'use strict';

angular.module('stepApp')
    .controller('UmracModuleSetupController',
        ['$scope', 'UmracModuleSetup', 'UmracModuleSetupSearch', 'ParseLinks',
        function ($scope, UmracModuleSetup, UmracModuleSetupSearch, ParseLinks) {
        $scope.umracModuleSetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            UmracModuleSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.umracModuleSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            UmracModuleSetup.get({id: id}, function(result) {
                $scope.umracModuleSetup = result;
                $('#deleteUmracModuleSetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UmracModuleSetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUmracModuleSetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            UmracModuleSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.umracModuleSetups = result;
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
            $scope.umracModuleSetup = {
                moduleId: null,
                moduleName: null,
                moduleUrl: null,
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
