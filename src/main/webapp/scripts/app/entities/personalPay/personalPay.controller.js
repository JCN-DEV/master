'use strict';

angular.module('stepApp')
    .controller('PersonalPayController', function ($scope, $state, $modal, PersonalPay, PersonalPaySearch, ParseLinks) {
      
        $scope.personalPays = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PersonalPay.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.personalPays = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PersonalPaySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.personalPays = result;
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
            $scope.personalPay = {
                effectiveDate: null,
                dateCreated: null,
                dateModified: null,
                amount: null,
                status: null,
                id: null
            };
        };
    });
