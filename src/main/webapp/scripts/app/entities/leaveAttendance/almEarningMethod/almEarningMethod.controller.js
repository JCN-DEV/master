'use strict';

angular.module('stepApp')
    .controller('AlmEarningMethodController',

    ['$scope', 'AlmEarningMethod', 'AlmEarningMethodSearch', 'ParseLinks',
    function ($scope, AlmEarningMethod, AlmEarningMethodSearch, ParseLinks) {
        $scope.almEarningMethods = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AlmEarningMethod.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almEarningMethods = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AlmEarningMethod.get({id: id}, function(result) {
                $scope.almEarningMethod = result;
                $('#deleteAlmEarningMethodConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmEarningMethod.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmEarningMethodConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmEarningMethodSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almEarningMethods = result;
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
            $scope.almEarningMethod = {
                earningMethodName: null,
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
