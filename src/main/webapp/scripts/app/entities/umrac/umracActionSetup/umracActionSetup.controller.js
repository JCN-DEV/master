'use strict';

angular.module('stepApp')
    .controller('UmracActionSetupController',
    ['$scope', 'UmracActionSetup', 'UmracActionSetupSearch', 'ParseLinks',
    function ($scope, UmracActionSetup, UmracActionSetupSearch, ParseLinks) {
        $scope.umracActionSetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            UmracActionSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.umracActionSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            UmracActionSetup.get({id: id}, function(result) {
                $scope.umracActionSetup = result;
                $('#deleteUmracActionSetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            UmracActionSetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUmracActionSetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            UmracActionSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.umracActionSetups = result;
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
            $scope.umracActionSetup = {
                actionId: null,
                actionName: null,
                actionUrl: null,
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
