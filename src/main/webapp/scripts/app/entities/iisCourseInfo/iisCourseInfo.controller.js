'use strict';

angular.module('stepApp')
    .controller('IisCourseInfoeeeeeeeeController',
    ['$scope','$state','$modal','IisCourseInfo','IisCourseInfoSearch','ParseLinks',
    function ($scope, $state, $modal, IisCourseInfo, IisCourseInfoSearch, ParseLinks) {

        $scope.iisCourseInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            IisCourseInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.iisCourseInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            IisCourseInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.iisCourseInfos = result;
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
            $scope.iisCourseInfo = {
                perDateEdu: null,
                perDateBteb: null,
                mpoEnlisted: null,
                dateMpo: null,
                seatNo: null,
                shift: null,
                id: null
            };
        };
    }]);
