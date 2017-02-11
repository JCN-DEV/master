'use strict';

angular.module('stepApp')
    .controller('EduBoardController',
    ['$scope', '$state', '$modal', 'EduBoard', 'EduBoardSearch', 'ParseLinks',
    function ($scope, $state, $modal, EduBoard, EduBoardSearch, ParseLinks) {

        $scope.eduBoards = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            EduBoard.query({page: $scope.page, size: 5000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.eduBoards = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            EduBoardSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.eduBoards = result;
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
            $scope.eduBoard = {
                name: null,
                description: null,
                status: null,
                id: null
            };
        };
    }]);
