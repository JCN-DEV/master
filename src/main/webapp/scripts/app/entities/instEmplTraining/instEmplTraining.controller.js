'use strict';

angular.module('stepApp')
    .controller('InstEmplTrainingController',
    ['$scope', '$state', '$modal', 'InstEmplTraining', 'InstEmplTrainingSearch', 'ParseLinks',
    function ($scope, $state, $modal, InstEmplTraining, InstEmplTrainingSearch, ParseLinks) {

        $scope.instEmplTrainings = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstEmplTraining.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instEmplTrainings = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstEmplTrainingSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instEmplTrainings = result;
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
            $scope.instEmplTraining = {
                name: null,
                subjectsCoverd: null,
                location: null,
                startedDate: null,
                endedDate: null,
                result: null,
                id: null
            };
        };
    }]);
