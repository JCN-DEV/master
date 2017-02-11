'use strict';

angular.module('stepApp')
    .controller('TraineeEvaluationInfoController', function ($scope, TraineeEvaluationInfo, TraineeEvaluationInfoSearch, ParseLinks) {
        $scope.traineeEvaluationInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TraineeEvaluationInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.traineeEvaluationInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            TraineeEvaluationInfo.get({id: id}, function(result) {
                $scope.traineeEvaluationInfo = result;
                $('#deleteTraineeEvaluationInfoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TraineeEvaluationInfo.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTraineeEvaluationInfoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            TraineeEvaluationInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.traineeEvaluationInfos = result;
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
            $scope.traineeEvaluationInfo = {
                sessionYear: null,
                performance: null,
                mark: null,
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
