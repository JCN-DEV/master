'use strict';

angular.module('stepApp')
    .controller('DearnessAssignController',
    ['$scope', 'DearnessAssign', 'DearnessAssignSearch', 'ParseLinks',
     function ($scope, DearnessAssign, DearnessAssignSearch, ParseLinks) {
        $scope.dearnessAssigns = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            DearnessAssign.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dearnessAssigns = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            DearnessAssign.get({id: id}, function(result) {
                $scope.dearnessAssign = result;
                $('#deleteDearnessAssignConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            DearnessAssign.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDearnessAssignConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            DearnessAssignSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.dearnessAssigns = result;
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
            $scope.dearnessAssign = {
                amount: null,
                effectiveDate: null,
                stopDate: null,
                status: null,
                createBy: null,
                createDate: null,
                updateBy: null,
                updateDate: null,
                remarks: null,
                id: null
            };
        };
    }]);
