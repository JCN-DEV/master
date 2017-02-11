'use strict';

angular.module('stepApp')
    .controller('PayScaleController',
    ['$scope', '$state', '$modal', 'PayScale', 'PayScaleSearch', 'ParseLinks',
    function ($scope, $state, $modal, PayScale, PayScaleSearch, ParseLinks) {

        $scope.payScales = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PayScale.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.payScales = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PayScaleSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.payScales = result;
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
            $scope.payScale = {
                code: null,
                payScaleClass: null,
                grade: null,
                gradeName: null,
                basicAmount: null,
                houseAllowance: null,
                medicalAllowance: null,
                welfareAmount: null,
                retirementAmount: null,
                date: null,
                id: null
            };
        };
    }]);
