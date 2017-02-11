'use strict';

angular.module('stepApp')
    .controller('JpEmployeeTrainingController',
    ['$scope', 'JpEmployeeTraining', 'JpEmployeeTrainingSearch', 'ParseLinks',
    function ($scope, JpEmployeeTraining, JpEmployeeTrainingSearch, ParseLinks) {
        $scope.jpEmployeeTrainings = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            JpEmployeeTraining.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jpEmployeeTrainings = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            JpEmployeeTraining.get({id: id}, function(result) {
                $scope.jpEmployeeTraining = result;
                $('#deleteJpEmployeeTrainingConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            JpEmployeeTraining.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteJpEmployeeTrainingConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            JpEmployeeTrainingSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.jpEmployeeTrainings = result;
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
            $scope.jpEmployeeTraining = {
                trainingTitle: null,
                topicCovered: null,
                institute: null,
                location: null,
                duration: null,
                result: null,
                id: null
            };
        };
    }]);
