'use strict';

angular.module('stepApp')
    .controller('IisCourseInfoTempController', function ($scope, $state, $modal, IisCourseInfoTemp, IisCourseInfoTempSearch, ParseLinks) {
      
        $scope.iisCourseInfoTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            IisCourseInfoTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.iisCourseInfoTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            IisCourseInfoTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.iisCourseInfoTemps = result;
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
            $scope.iisCourseInfoTemp = {
                perDateEdu: null,
                perDateBteb: null,
                mpoEnlisted: null,
                dateMpo: null,
                seatNo: null,
                shift: null,
                createDate: null,
                updateDate: null,
                id: null
            };
        };
    });
