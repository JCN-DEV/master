'use strict';

angular.module('stepApp')
    .controller('AlmWorkingUnitController',
    ['$scope', 'AlmWorkingUnit', 'AlmWorkingUnitSearch', 'ParseLinks',
    function ($scope, AlmWorkingUnit, AlmWorkingUnitSearch, ParseLinks) {
        $scope.almWorkingUnits = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AlmWorkingUnit.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almWorkingUnits = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AlmWorkingUnit.get({id: id}, function(result) {
                $scope.almWorkingUnit = result;
                $('#deleteAlmWorkingUnitConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmWorkingUnit.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmWorkingUnitConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmWorkingUnitSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almWorkingUnits = result;
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
            $scope.almWorkingUnit = {
                isWeekend: false,
                dayName: null,
                officeStart: null,
                delayTime: null,
                absentTime: null,
                officeEnd: null,
                effectiveDate: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
