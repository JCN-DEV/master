'use strict';

angular.module('stepApp')
    .controller('InformationCorrectionController', function ($scope, $state, $modal, InformationCorrection, InformationCorrectionSearch, ParseLinks) {
      
        $scope.informationCorrections = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InformationCorrection.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.informationCorrections = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InformationCorrectionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.informationCorrections = result;
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
            $scope.informationCorrection = {
                dob: null,
                name: null,
                indexNo: null,
                bankAccountNo: null,
                adForwarded: null,
                dgFinalApproval: null,
                createdDate: null,
                modifiedDate: null,
                directorComment: null,
                status: null,
                id: null
            };
        };
    });
