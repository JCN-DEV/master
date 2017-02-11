'use strict';

angular.module('stepApp')
    .controller('PgmsAppFamilyPensionController',
    ['$scope', 'PgmsAppFamilyPension', 'PgmsAppFamilyPensionSearch', 'ParseLinks',
    function ($scope, PgmsAppFamilyPension, PgmsAppFamilyPensionSearch, ParseLinks) {
        $scope.pgmsAppFamilyPensions = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PgmsAppFamilyPension.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pgmsAppFamilyPensions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PgmsAppFamilyPension.get({id: id}, function(result) {
                $scope.pgmsAppFamilyPension = result;
                $('#deletePgmsAppFamilyPensionConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PgmsAppFamilyPension.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePgmsAppFamilyPensionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PgmsAppFamilyPensionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pgmsAppFamilyPensions = result;
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
            $scope.pgmsAppFamilyPension = {
                empName: null,
                empDepartment: null,
                empDesignation: null,
                empNid: null,
                nomineeStatus: null,
                nomineName: null,
                nomineDob: null,
                nomineGender: null,
                nomineRelation: null,
                nomineOccupation: null,
                nomineDesignation: null,
                nominePreAddress: null,
                nomineParAddress: null,
                nomineNid: null,
                nomineContNo: null,
                nomineBankName: null,
                nomineBranchName: null,
                nomineAccNo: null,
                applyDate: null,
                aprvStatus: 'Pending',
                aprvDate: null,
                aprvComment: null,
                aprvBy: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateBy: null,
                updateDate: null,
                id: null
            };
        };
    }]);
