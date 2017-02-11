'use strict';

angular.module('stepApp')
    .controller('AlmLeavGrpTypeMapController',
    ['$scope', 'AlmLeavGrpTypeMap', 'AlmLeavGrpTypeMapSearch', 'ParseLinks',
    function ($scope, AlmLeavGrpTypeMap, AlmLeavGrpTypeMapSearch, ParseLinks) {
        $scope.almLeavGrpTypeMaps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AlmLeavGrpTypeMap.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almLeavGrpTypeMaps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AlmLeavGrpTypeMap.get({id: id}, function(result) {
                $scope.almLeavGrpTypeMap = result;
                $('#deleteAlmLeavGrpTypeMapConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmLeavGrpTypeMap.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmLeavGrpTypeMapConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmLeavGrpTypeMapSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almLeavGrpTypeMaps = result;
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
            $scope.almLeavGrpTypeMap = {
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
