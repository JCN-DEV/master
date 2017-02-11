'use strict';

angular.module('stepApp')
    .controller('TrainingCategorySetupController', function ($scope, TrainingCategorySetup, TrainingCategorySetupSearch, ParseLinks) {
        $scope.trainingCategorySetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TrainingCategorySetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.trainingCategorySetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            TrainingCategorySetup.get({id: id}, function(result) {
                $scope.trainingCategorySetup = result;
                $('#deleteTrainingCategorySetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TrainingCategorySetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTrainingCategorySetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            TrainingCategorySetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.trainingCategorySetups = result;
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
            $scope.trainingCategorySetup = {
                categoryName: null,
                description: null,
                status: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
