'use strict';

angular.module('stepApp')
    .controller('UmracSubmoduleSetupController',
        ['$scope', 'UmracSubmoduleSetup', 'UmracSubmoduleSetupSearch', 'ParseLinks',
        function ($scope, UmracSubmoduleSetup, UmracSubmoduleSetupSearch, ParseLinks) {
        $scope.umracSubmoduleSetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            UmracSubmoduleSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.umracSubmoduleSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            UmracSubmoduleSetup.get({id: id}, function(result) {
                $scope.umracSubmoduleSetup = result;
                $('#deleteUmracSubmoduleSetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UmracSubmoduleSetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUmracSubmoduleSetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            UmracSubmoduleSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.umracSubmoduleSetups = result;
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
            $scope.umracSubmoduleSetup = {
                subModuleId: null,
                subModuleName: null,
                subModuleUrl: null,
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
