'use strict';

angular.module('stepApp')
    .controller('PgmsPenGrRateController',
    ['$scope', 'PgmsPenGrRate', 'PgmsPenGrRateSearch',
    function ($scope, PgmsPenGrRate, PgmsPenGrRateSearch) {
        $scope.pgmsPenGrRates = [];
        $scope.loadAll = function() {
            PgmsPenGrRate.query(function(result) {
               $scope.pgmsPenGrRates = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PgmsPenGrRate.get({id: id}, function(result) {
                $scope.pgmsPenGrRate = result;
                $('#deletePgmsPenGrRateConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PgmsPenGrRate.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePgmsPenGrRateConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PgmsPenGrRateSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pgmsPenGrRates = result;
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
            $scope.pgmsPenGrRate = {
                penGrSetId: null,
                workingYear: null,
                rateOfPenGr: null,
                activeStatus: null,
                id: null
            };
        };
    }]);
