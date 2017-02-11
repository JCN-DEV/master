'use strict';

angular.module('stepApp')
    .controller('IncrementSetupController',
    ['$scope','IncrementSetup','IncrementSetupSearch','ParseLinks',
    function ($scope, IncrementSetup, IncrementSetupSearch, ParseLinks) {
        $scope.incrementSetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            IncrementSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.incrementSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            IncrementSetup.get({id: id}, function(result) {
                $scope.incrementSetup = result;
                $('#deleteIncrementSetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            IncrementSetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteIncrementSetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            IncrementSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.incrementSetups = result;
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
            $scope.incrementSetup = {
                amount: null,
                status: null,
                createBy: null,
                createDate: null,
                updateBy: null,
                updateDate: null,
                remarks: null,
                PayCodeSerial: null,
                id: null
            };
        };
    }]);
