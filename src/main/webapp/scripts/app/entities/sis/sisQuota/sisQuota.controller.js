'use strict';

angular.module('stepApp')
    .controller('SisQuotaController',
    ['$scope', 'SisQuota', 'SisQuotaSearch', 'ParseLinks',
    function ($scope, SisQuota, SisQuotaSearch, ParseLinks) {
        $scope.sisQuotas = [];
        $scope.page = 0;
        $scope.currentPage = 1;
        $scope.pageSize = 10;
        $scope.loadAll = function() {
            SisQuota.query({page: $scope.page, size: 1000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.sisQuotas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            SisQuota.get({id: id}, function(result) {
                $scope.sisQuota = result;
                $('#deleteSisQuotaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            SisQuota.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSisQuotaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            SisQuotaSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.sisQuotas = result;
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
            $scope.sisQuota = {
                name: null,
                description: null,
                isFor: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
