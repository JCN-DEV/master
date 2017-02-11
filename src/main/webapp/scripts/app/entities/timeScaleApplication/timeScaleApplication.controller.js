'use strict';

angular.module('stepApp')
    .controller('TimeScaleApplicationController',
    ['$scope', '$state', '$modal', 'TimeScaleApplication', 'TimeScaleApplicationSearch', 'ParseLinks',
    function ($scope, $state, $modal, TimeScaleApplication, TimeScaleApplicationSearch, ParseLinks) {

        $scope.timeScaleApplications = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TimeScaleApplication.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.timeScaleApplications = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            TimeScaleApplicationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.timeScaleApplications = result;
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
            $scope.timeScaleApplication = {
                date: null,
                indexNo: null,
                status: null,
                resulationDate: null,
                agendaNumber: null,
                workingBreak: null,
                workingBreakStart: null,
                workingBreakEnd: null,
                disciplinaryAction: null,
                disActionCaseNo: null,
                disActionFileName: null,
                id: null
            };
        };
    }]);
