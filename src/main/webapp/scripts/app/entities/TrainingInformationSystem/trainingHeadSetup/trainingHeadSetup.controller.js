'use strict';

angular.module('stepApp')
    .controller('TrainingHeadSetupController', function ($scope, TrainingHeadSetup, TrainingHeadSetupSearch, ParseLinks) {
        $scope.trainingHeadSetups = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TrainingHeadSetup.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.trainingHeadSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            TrainingHeadSetup.get({id: id}, function(result) {
                $scope.trainingHeadSetup = result;
                $('#deleteTrainingHeadSetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TrainingHeadSetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTrainingHeadSetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            TrainingHeadSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.trainingHeadSetups = result;
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
            $scope.trainingHeadSetup = {
                headName: null,
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
