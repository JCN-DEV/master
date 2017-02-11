'use strict';

angular.module('stepApp')
    .controller('UmracRightsSetupController',
    ['$scope', 'UmracRightsSetup', 'UmracRightsSetupSearch', 'ParseLinks',
    function ($scope, UmracRightsSetup, UmracRightsSetupSearch, ParseLinks) {
        $scope.umracRightsSetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            UmracRightsSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.umracRightsSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            UmracRightsSetup.get({id: id}, function(result) {
                $scope.umracRightsSetup = result;
                $('#deleteUmracRightsSetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UmracRightsSetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUmracRightsSetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            UmracRightsSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.umracRightsSetups = result;
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
            $scope.umracRightsSetup = {
                rightId: null,
                roleId: null,
                module_id: null,
                subModule_id: null,
                rights: null,
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
