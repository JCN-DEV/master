'use strict';

angular.module('stepApp')
    .controller('TrainingBudgetInformationController', function ($scope, TrainingBudgetInformation, TrainingBudgetInformationSearch, ParseLinks) {
        $scope.trainingBudgetInformations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TrainingBudgetInformation.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.trainingBudgetInformations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            TrainingBudgetInformation.get({id: id}, function(result) {
                $scope.trainingBudgetInformation = result;
                $('#deleteTrainingBudgetInformationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TrainingBudgetInformation.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTrainingBudgetInformationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            TrainingBudgetInformationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.trainingBudgetInformations = result;
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
            $scope.trainingBudgetInformation = {
                budgetAmount: null,
                expenseAmount: null,
                remarks: null,
                status: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
