'use strict';

angular.module('stepApp')
    .controller('PfmsDeductionFinalizeController', function ($scope, $rootScope, PfmsDeductionFinalize, PfmsDeductionFinalizeSearch, ParseLinks) {
        $scope.pfmsDeductionFinalizes = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PfmsDeductionFinalize.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pfmsDeductionFinalizes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PfmsDeductionFinalize.get({id: id}, function(result) {
                $scope.pfmsDeductionFinalize = result;
                $('#deletePfmsDeductionFinalizeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PfmsDeductionFinalize.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePfmsDeductionFinalizeConfirmation').modal('hide');
                    $scope.clear();
                    $rootScope.setErrorMessage('stepApp.pfmsDeductionFinalize.deleted');
                });
        };

        $scope.search = function () {
            PfmsDeductionFinalizeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pfmsDeductionFinalizes = result;
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
            $scope.pfmsDeductionFinalize = {
                finalizeYear: null,
                finalizeMonth: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
