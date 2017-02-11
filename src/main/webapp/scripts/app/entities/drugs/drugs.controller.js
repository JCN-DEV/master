'use strict';

angular.module('stepApp')
    .controller('DrugsController', function ($scope, Drugs, DrugsSearch, ParseLinks) {
        $scope.drugss = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Drugs.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.drugss = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Drugs.get({id: id}, function(result) {
                $scope.drugs = result;
                $('#deleteDrugsConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Drugs.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDrugsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            DrugsSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.drugss = result;
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
            $scope.drugs = {
                drugsFor: null,
                drugClass: null,
                brandName: null,
                status: null,
                createBy: null,
                createDate: null,
                updateBy: null,
                updateDate: null,
                remarks: null,
                contains: null,
                dosageForm: null,
                manufacturer: null,
                price: null,
                types: null,
                id: null
            };
        };
    });
