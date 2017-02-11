'use strict';

angular.module('stepApp')
    .controller('AlmLeaveGroupController',
    ['$scope', 'AlmLeaveGroup', 'AlmLeaveGroupSearch', 'ParseLinks',
    function ($scope, AlmLeaveGroup, AlmLeaveGroupSearch, ParseLinks) {
        $scope.almLeaveGroups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AlmLeaveGroup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almLeaveGroups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AlmLeaveGroup.get({id: id}, function(result) {
                $scope.almLeaveGroup = result;
                $('#deleteAlmLeaveGroupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmLeaveGroup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmLeaveGroupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmLeaveGroupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almLeaveGroups = result;
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
            $scope.almLeaveGroup = {
                leaveGroupName: null,
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
