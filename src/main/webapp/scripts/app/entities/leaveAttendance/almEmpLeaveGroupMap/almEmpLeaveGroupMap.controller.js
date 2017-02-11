'use strict';

angular.module('stepApp')
    .controller('AlmEmpLeaveGroupMapController',
    ['$scope', 'AlmEmpLeaveGroupMap', 'AlmEmpLeaveGroupMapSearch', 'ParseLinks',
    function ($scope, AlmEmpLeaveGroupMap, AlmEmpLeaveGroupMapSearch, ParseLinks) {
        $scope.almEmpLeaveGroupMaps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AlmEmpLeaveGroupMap.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almEmpLeaveGroupMaps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AlmEmpLeaveGroupMap.get({id: id}, function(result) {
                $scope.almEmpLeaveGroupMap = result;
                $('#deleteAlmEmpLeaveGroupMapConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmEmpLeaveGroupMap.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmEmpLeaveGroupMapConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmEmpLeaveGroupMapSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almEmpLeaveGroupMaps = result;
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
            $scope.almEmpLeaveGroupMap = {
                effectiveDate: null,
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
