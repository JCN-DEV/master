'use strict';

angular.module('stepApp')
    .controller('InstComiteFormationController',
    ['$scope','$state','$modal','InstComiteFormation','InstComiteFormationSearch','ParseLinks',
    function ($scope, $state, $modal, InstComiteFormation, InstComiteFormationSearch, ParseLinks) {

        $scope.instComiteFormations = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstComiteFormation.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instComiteFormations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstComiteFormationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instComiteFormations = result;
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
            $scope.instComiteFormation = {
                comiteName: null,
                comiteType: null,
                address: null,
                timeFrom: null,
                timeTo: null,
                formationDate: null,
                id: null
            };
        };
    }]);
