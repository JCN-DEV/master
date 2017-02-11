'use strict';

angular.module('stepApp')
    .controller('MpoCommitteePersonInfoController',
    ['$scope', '$state', '$modal', 'MpoCommitteePersonInfo', 'MpoCommitteePersonInfoSearch', 'ParseLinks',
    function ($scope, $state, $modal, MpoCommitteePersonInfo, MpoCommitteePersonInfoSearch, ParseLinks) {

        $scope.mpoCommitteePersonInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            MpoCommitteePersonInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.mpoCommitteePersonInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            MpoCommitteePersonInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.mpoCommitteePersonInfos = result;
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
            $scope.mpoCommitteePersonInfo = {
                contactNo: null,
                address: null,
                designation: null,
                orgName: null,
                dateCrated: null,
                dateModified: null,
                status: null,
                activated: null,
                id: null
            };
        };
    }]);
