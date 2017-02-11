'use strict';

angular.module('stepApp')
    .controller('AlmLeaveTypeController',
    ['$scope', 'AlmLeaveType', 'AlmLeaveTypeSearch', 'ParseLinks',
    function ($scope, AlmLeaveType, AlmLeaveTypeSearch, ParseLinks) {
        $scope.almLeaveTypes = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AlmLeaveType.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almLeaveTypes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AlmLeaveType.get({id: id}, function(result) {
                $scope.almLeaveType = result;
                $('#deleteAlmLeaveTypeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmLeaveType.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmLeaveTypeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmLeaveTypeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almLeaveTypes = result;
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
            $scope.almLeaveType = {
                leaveTypeName: null,
                description: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
