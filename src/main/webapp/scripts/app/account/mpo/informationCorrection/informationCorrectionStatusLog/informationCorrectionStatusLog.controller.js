'use strict';

angular.module('stepApp')
    .controller('InformationCorrectionStatusLogController', function ($scope, $state, $modal, InformationCorrectionStatusLog, InformationCorrectionStatusLogSearch, ParseLinks) {
      
        $scope.informationCorrectionStatusLogs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InformationCorrectionStatusLog.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.informationCorrectionStatusLogs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InformationCorrectionStatusLogSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.informationCorrectionStatusLogs = result;
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
            $scope.informationCorrectionStatusLog = {
                status: null,
                remarks: null,
                fromDate: null,
                toDate: null,
                cause: null,
                id: null
            };
        };
    });
