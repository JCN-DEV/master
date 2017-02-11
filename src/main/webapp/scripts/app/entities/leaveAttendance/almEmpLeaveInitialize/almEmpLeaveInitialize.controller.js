'use strict';

angular.module('stepApp')
    .controller('AlmEmpLeaveInitializeController', function ($scope, AlmEmpLeaveInitialize, AlmEmpLeaveInitializeSearch, ParseLinks) {
        $scope.almEmpLeaveInitializes = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AlmEmpLeaveInitialize.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almEmpLeaveInitializes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AlmEmpLeaveInitialize.get({id: id}, function(result) {
                $scope.almEmpLeaveInitialize = result;
                $('#deleteAlmEmpLeaveInitializeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmEmpLeaveInitialize.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmEmpLeaveInitializeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmEmpLeaveInitializeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almEmpLeaveInitializes = result;
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
            $scope.almEmpLeaveInitialize = {
                effectiveDate: null,
                year: null,
                leaveEarned: null,
                leaveTaken: null,
                leaveForwarded: null,
                leaveOnApply: null,
                leaveBalance: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
