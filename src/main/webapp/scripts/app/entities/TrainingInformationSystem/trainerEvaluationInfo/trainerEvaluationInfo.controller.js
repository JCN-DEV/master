'use strict';

angular.module('stepApp')
    .controller('TrainerEvaluationInfoController', function ($scope, TrainerEvaluationInfo, TrainerEvaluationInfoSearch, ParseLinks) {
        $scope.trainerEvaluationInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TrainerEvaluationInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.trainerEvaluationInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            TrainerEvaluationInfo.get({id: id}, function(result) {
                $scope.trainerEvaluationInfo = result;
                $('#deleteTrainerEvaluationInfoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TrainerEvaluationInfo.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTrainerEvaluationInfoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            TrainerEvaluationInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.trainerEvaluationInfos = result;
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
            $scope.trainerEvaluationInfo = {
                sessionYear: null,
                performance: null,
                remarks: null,
                evaluationDate: null,
                status: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
