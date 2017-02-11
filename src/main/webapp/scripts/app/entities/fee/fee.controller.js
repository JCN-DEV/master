'use strict';

angular.module('stepApp')
    .controller('FeeController',
    ['$scope', '$state', 'DataUtils',
    function ($scope, $state, DataUtils) {
        /*$scope.fees = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Fee.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.fees = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Fee.get({id: id}, function(result) {
                $scope.fee = result;
                $('#deleteFeeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Fee.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFeeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            FeeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.fees = result;
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
            $scope.fee = {
                feeId: null,
                id: null
            };
        };*/
    }]);
