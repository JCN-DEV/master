'use strict';

angular.module('stepApp')
    .controller('TrainingInitializationInfoController', function ($scope, TrainingInitializationInfo, TrainingInitializationInfoSearch, ParseLinks) {
        $scope.trainingInitializationInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TrainingInitializationInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.trainingInitializationInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            TrainingInitializationInfo.get({id: id}, function(result) {
                $scope.trainingInitializationInfo = result;
                $('#deleteTrainingInitializationInfoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TrainingInitializationInfo.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTrainingInitializationInfoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            TrainingInitializationInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.trainingInitializationInfos = result;
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
            $scope.trainingInitializationInfo = {
                trainingCode: null,
                trainingType: null,
                session: null,
                venueName: null,
                numberOfTrainee: null,
                location: null,
                division: null,
                district: null,
                venueDescription: null,
                initializationDate: null,
                startDate: null,
                endDate: null,
                duration: null,
                hours: null,
                publishStatus: null,
                status: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
