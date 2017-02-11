'use strict';

angular.module('stepApp')
    .controller('IisCurriculumInfoTempController', function ($scope, $state, $modal, IisCurriculumInfoTemp, IisCurriculumInfoTempSearch, ParseLinks) {
      
        $scope.iisCurriculumInfoTemps = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            IisCurriculumInfoTemp.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.iisCurriculumInfoTemps = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            IisCurriculumInfoTempSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.iisCurriculumInfoTemps = result;
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
            $scope.iisCurriculumInfoTemp = {
                firstDate: null,
                lastDate: null,
                mpoEnlisted: null,
                recNo: null,
                mpoDate: null,
                createDate: null,
                updateDate: null,
                id: null
            };
        };
    });
