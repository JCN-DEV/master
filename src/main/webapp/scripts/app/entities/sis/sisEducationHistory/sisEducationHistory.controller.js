'use strict';

angular.module('stepApp')
    .controller('SisEducationHistoryController',
    ['$scope', 'SisEducationHistory', 'SisEducationHistorySearch', 'ParseLinks',
    function ($scope, SisEducationHistory, SisEducationHistorySearch, ParseLinks) {
        $scope.sisEducationHistorys = [];
        $scope.page = 0;
        $scope.currentPage = 1;
        $scope.pageSize = 10;
        $scope.loadAll = function() {
            SisEducationHistory.query({page: $scope.page, size: 1000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.sisEducationHistorys = result;
                console.log($scope.sisEducationHistorys);
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            SisEducationHistory.get({id: id}, function(result) {
                $scope.sisEducationHistory = result;
                $('#deleteSisEducationHistoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            SisEducationHistory.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSisEducationHistoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            SisEducationHistorySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.sisEducationHistorys = result;
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
            $scope.sisEducationHistory = {
                yearOrSemester: null,
                rollNo: null,
                majorOrDept: null,
                divisionOrGpa: null,
                passingYear: null,
                achievedCertificate: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
