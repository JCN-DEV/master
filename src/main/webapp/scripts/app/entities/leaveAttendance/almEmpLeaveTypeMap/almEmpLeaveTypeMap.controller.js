'use strict';

angular.module('stepApp')
    .controller('AlmEmpLeaveTypeMapController',

    ['$scope', 'AlmEmpLeaveTypeMap', 'AlmEmpLeaveTypeMapSearch', 'ParseLinks',
    function ($scope, AlmEmpLeaveTypeMap, AlmEmpLeaveTypeMapSearch, ParseLinks) {
        $scope.almEmpLeaveTypeMaps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AlmEmpLeaveTypeMap.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almEmpLeaveTypeMaps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AlmEmpLeaveTypeMap.get({id: id}, function(result) {
                $scope.almEmpLeaveTypeMap = result;
                $('#deleteAlmEmpLeaveTypeMapConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmEmpLeaveTypeMap.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmEmpLeaveTypeMapConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmEmpLeaveTypeMapSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almEmpLeaveTypeMaps = result;
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
            $scope.almEmpLeaveTypeMap = {
                effectiveDate: null,
                currentBalance: null,
                newBalance: null,
                isAddition: null,
                reason: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
