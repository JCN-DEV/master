'use strict';

angular.module('stepApp')
    .controller('PgmsAppRetirmntNmineController',
    ['$scope', 'PgmsAppRetirmntNmine', 'PgmsAppRetirmntNmineSearch', 'ParseLinks',
    function ($scope, PgmsAppRetirmntNmine, PgmsAppRetirmntNmineSearch, ParseLinks) {
        $scope.pgmsAppRetirmntNmines = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PgmsAppRetirmntNmine.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pgmsAppRetirmntNmines = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PgmsAppRetirmntNmine.get({id: id}, function(result) {
                $scope.pgmsAppRetirmntNmine = result;
                $('#deletePgmsAppRetirmntNmineConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PgmsAppRetirmntNmine.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePgmsAppRetirmntNmineConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PgmsAppRetirmntNmineSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pgmsAppRetirmntNmines = result;
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
            $scope.pgmsAppRetirmntNmine = {
                appRetirmntPenId: null,
                nomineeStatus: null,
                nomineeName: null,
                gender: null,
                relation: null,
                dateOfBirth: null,
                presentAddress: null,
                nid: null,
                occupation: null,
                designation: null,
                maritalStatus: null,
                mobileNo: null,
                getPension: null,
                hrNomineeInfo: null,
                id: null
            };
        };
    }]);
