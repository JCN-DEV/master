'use strict';

angular.module('stepApp')
    .controller('APScaleApplicationController',
    ['$scope', '$state', '$modal', 'APScaleApplication', 'APScaleApplicationSearch', 'ParseLinks',
     function ($scope, $state, $modal, APScaleApplication, APScaleApplicationSearch, ParseLinks) {

        $scope.aPScaleApplications = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            APScaleApplication.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.aPScaleApplications = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            APScaleApplicationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.aPScaleApplications = result;
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
            $scope.aPScaleApplication = {
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
