'use strict';

angular.module('stepApp')
    .controller('TraineeInformationController', function ($scope, TraineeInformation, TraineeInformationSearch, ParseLinks) {
        $scope.traineeInformations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TraineeInformation.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.traineeInformations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            TraineeInformation.get({id: id}, function(result) {
                $scope.traineeInformation = result;
                $('#deleteTraineeInformationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TraineeInformation.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTraineeInformationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            TraineeInformationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.traineeInformations = result;
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
            $scope.traineeInformation = {
                traineeId: null,
                traineeName: null,
                session: null,
                gender: null,
                organization: null,
                address: null,
                division: null,
                district: null,
                contactNumber: null,
                status: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
