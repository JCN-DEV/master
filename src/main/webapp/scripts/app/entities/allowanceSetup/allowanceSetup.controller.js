'use strict';

angular.module('stepApp')
    .controller('AllowanceSetupController',
    ['$scope', 'AllowanceSetup', 'AllowanceSetupSearch', 'ParseLinks',
    function ($scope, AllowanceSetup, AllowanceSetupSearch, ParseLinks) {
        $scope.allowanceSetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AllowanceSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.allowanceSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AllowanceSetup.get({id: id}, function(result) {
                $scope.allowanceSetup = result;
                $('#deleteAllowanceSetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AllowanceSetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAllowanceSetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AllowanceSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.allowanceSetups = result;
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

        $scope.calendar = {
            opened: {},
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };

        $scope.clear = function () {
            $scope.allowanceSetup = {
                name: null,
                status: null,
                createBy: null,
                createDate: null,
                updateBy: null,
                updateDate: null,
                remarks: null,
                effectiveDate: null,
                id: null
            };
        };
    }]);
