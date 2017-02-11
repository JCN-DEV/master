'use strict';

angular.module('stepApp')
    .controller('InstEmpSpouseInfoController',
    ['$scope', '$state', '$modal', 'InstEmpSpouseInfo', 'InstEmpSpouseInfoSearch', 'ParseLinks',
    function ($scope, $state, $modal, InstEmpSpouseInfo, InstEmpSpouseInfoSearch, ParseLinks) {

        $scope.instEmpSpouseInfos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            InstEmpSpouseInfo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.instEmpSpouseInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            InstEmpSpouseInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.instEmpSpouseInfos = result;
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
            $scope.instEmpSpouseInfo = {
                name: null,
                dob: null,
                isNominee: false,
                gender: null,
                relation: null,
                nomineePercentage: null,
                occupation: null,
                tin: null,
                nid: null,
                designation: null,
                govJobId: null,
                mobile: null,
                officeContact: null,
                id: null
            };
        };
    }]);
