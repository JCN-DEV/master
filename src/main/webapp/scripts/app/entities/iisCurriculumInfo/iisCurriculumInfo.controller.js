'use strict';

angular.module('stepApp')
    .controller('IisCurriculumInfoeeeeeeeeeeController',
    ['$scope','$state','$modal','IisCurriculumInfo','IisCurriculumInfoSearch','ParseLinks',
    function ($scope, $state, $modal, IisCurriculumInfo, IisCurriculumInfoSearch, ParseLinks) {

        $scope.iisCurriculumInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            IisCurriculumInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.iisCurriculumInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            IisCurriculumInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.iisCurriculumInfos = result;
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
            $scope.iisCurriculumInfo = {
                firstDate: null,
                lastDate: null,
                mpoEnlisted: null,
                recNo: null,
                mpoDate: null,
                id: null
            };
        };
    }]);
