'use strict';

angular.module('stepApp')
    .controller('RisAppFormEduQController', function ($scope, RisAppFormEduQ, RisAppFormEduQSearch, ParseLinks) {
        $scope.risAppFormEduQs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            RisAppFormEduQ.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.risAppFormEduQs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            RisAppFormEduQ.get({id: id}, function(result) {
                $scope.risAppFormEduQ = result;
                $('#deleteRisAppFormEduQConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            RisAppFormEduQ.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteRisAppFormEduQConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            RisAppFormEduQSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.risAppFormEduQs = result;
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
            $scope.risAppFormEduQ = {
                examName: null,
                subject: null,
                educationalInstitute: null,
                passingYear: null,
                boardUniversity: null,
                additionalInformation: null,
                experience: null,
                qouta: null,
                id: null
            };
        };
    });
