'use strict';

angular.module('stepApp')
    .controller('TrainerInformationController', function ($scope, TrainerInformation, TrainerInformationSearch, ParseLinks) {
        $scope.trainerInformations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TrainerInformation.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.trainerInformations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            TrainerInformation.get({id: id}, function(result) {
                $scope.trainerInformation = result;
                $('#deleteTrainerInformationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TrainerInformation.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTrainerInformationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            TrainerInformationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.trainerInformations = result;
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
            $scope.trainerInformation = {
                trainerId: null,
                trainerName: null,
                trainerType: null,
                address: null,
                designation: null,
                department: null,
                organization: null,
                mobileNumber: null,
                emailId: null,
                specialSkills: null,
                trainingAssignStatus: null,
                status: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    });
