'use strict';

angular.module('stepApp')
    .controller('JpEmploymentHistoryController',
    ['$scope', 'JpEmploymentHistory', 'JpEmploymentHistorySearch', 'ParseLinks',
    function ($scope, JpEmploymentHistory, JpEmploymentHistorySearch, ParseLinks) {
        $scope.jpEmploymentHistorys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            JpEmploymentHistory.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jpEmploymentHistorys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            JpEmploymentHistory.get({id: id}, function(result) {
                $scope.jpEmploymentHistory = result;
                $('#deleteJpEmploymentHistoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            JpEmploymentHistory.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteJpEmploymentHistoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            JpEmploymentHistorySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.jpEmploymentHistorys = result;
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
            $scope.jpEmploymentHistory = {
                companyName: null,
                companyBusiness: null,
                companyLocation: null,
                positionHeld: null,
                departmentName: null,
                responsibility: null,
                startFrom: null,
                endTo: null,
                currentStatus: null,
                id: null
            };
        };
    }]);
