'use strict';

angular.module('stepApp')
    .controller('SearchController',
    ['$scope', '$stateParams', '$filter', '$location', 'entity', '$state', 'Auth', 'Principal', 'ParseLinks', '$timeout', 'Cat', 'Job', 'JobSearch',
    function ($scope, $stateParams, $filter, $location, entity, $state, Auth, Principal, ParseLinks, $timeout, Cat, Job, JobSearch) {
        Principal.identity().then(function (account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
console.log(entity);
        $scope.time = 0;
        $scope.total = 0;
        $scope.page = 0;
        $scope.per_page = 10;
        $scope.jobs = [];
        $scope.cats = [];
        $scope.jobs = entity;
        console.log(entity);
        console.log($scope.jobs);
        $scope.total = entity.length;
        $scope.q = $stateParams.q;

        Cat.query({page: 0, size: -1}, function (result, headers) {
            $scope.cats = result;
        });
        //$scope.today = new Date();
        $scope.date = new Date();
        console.log($scope.date);
        $scope.loadAll = function () {
            /*JobSearch.query({query: $scope.q}, function (result, headers) {
                $scope.jobs = result.slice(0, 8);
                $scope.total = result.length;
            }, function (response) {
            });*/
        };

        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

        $timeout(function () {
            angular.element('[ng-model="q"]').focus();
        });

        $scope.submit = function () {
            var form = $scope.form;
            $scope.submitted = true;
            if (form.$valid) {
                console.log($scope.q);
                var searchData = {'q': $scope.q};
                $state.go('search', searchData);
            }
        };

    }]);
