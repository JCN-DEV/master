'use strict';

angular.module('stepApp')
    .controller('PgmsPenRulesController',

    ['$scope', '$rootScope', 'PgmsPenRules', 'PgmsPenRulesSearch', 'ParseLinks',
    function ($scope, $rootScope, PgmsPenRules, PgmsPenRulesSearch, ParseLinks) {
        $scope.pgmsPenRuless = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PgmsPenRules.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pgmsPenRuless = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PgmsPenRules.get({id: id}, function(result) {
                $scope.pgmsPenRules = result;
                $('#deletePgmsPenRulesConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PgmsPenRules.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePgmsPenRulesConfirmation').modal('hide');
                    $rootScope.setErrorMessage('stepApp.pgmsPenRules.deleted');
                    $scope.clear();

                });

        };

        $scope.search = function () {
            PgmsPenRulesSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pgmsPenRuless = result;
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
            $scope.pgmsPenRules = {
                quotaType: null,
                minAgeLimit: null,
                maxAgeLimit: null,
                minWorkDuration: null,
                disable: null,
                senile: null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
