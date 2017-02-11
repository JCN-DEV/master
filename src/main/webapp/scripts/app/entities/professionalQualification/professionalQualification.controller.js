'use strict';

angular.module('stepApp')
    .controller('ProfessionalQualificationController',
    ['$scope','$state','$modal','ProfessionalQualification','ProfessionalQualificationSearch','ParseLinks',
    function ($scope, $state, $modal, ProfessionalQualification, ProfessionalQualificationSearch, ParseLinks) {

        $scope.professionalQualifications = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            ProfessionalQualification.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.professionalQualifications = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ProfessionalQualificationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.professionalQualifications = result;
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
            $scope.professionalQualification = {
                name: null,
                location: null,
                dateFrom: null,
                dateTo: null,
                duration: null,
                id: null
            };
        };
    }]);
