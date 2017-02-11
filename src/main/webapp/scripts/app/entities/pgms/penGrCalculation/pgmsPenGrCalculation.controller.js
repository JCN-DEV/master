'use strict';

angular.module('stepApp')
    .controller('PgmsPenGrCalculationController',
    ['$scope', 'PgmsPenGrCalculation', 'PgmsPenGrCalculationSearch', 'ParseLinks',
    function ($scope, PgmsPenGrCalculation, PgmsPenGrCalculationSearch, ParseLinks) {
        $scope.pgmsPenGrCalculations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PgmsPenGrCalculation.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pgmsPenGrCalculations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PgmsPenGrCalculation.get({id: id}, function(result) {
                $scope.pgmsPenGrCalculation = result;
                $('#deletePgmsPenGrCalculationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PgmsPenGrCalculation.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePgmsPenGrCalculationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PgmsPenGrCalculationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pgmsPenGrCalculations = result;
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
            $scope.pgmsPenGrCalculation = {
                withdrawnType: null,
                categoryType: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
