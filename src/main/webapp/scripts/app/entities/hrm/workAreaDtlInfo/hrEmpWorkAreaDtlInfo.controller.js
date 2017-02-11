'use strict';

angular.module('stepApp')
    .controller('HrEmpWorkAreaDtlInfoController',
    ['$scope', '$state', 'HrEmpWorkAreaDtlInfo', 'HrEmpWorkAreaDtlInfoSearch', 'ParseLinks',
    function ($scope, $state, HrEmpWorkAreaDtlInfo, HrEmpWorkAreaDtlInfoSearch, ParseLinks) {

        $scope.hrEmpWorkAreaDtlInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            HrEmpWorkAreaDtlInfo.query({page: $scope.page - 1, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmpWorkAreaDtlInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            HrEmpWorkAreaDtlInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEmpWorkAreaDtlInfos = result;
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
            $scope.hrEmpWorkAreaDtlInfo = {
                name: null,
                establishmentDate: null,
                contactNumber: null,
                address: null,
                telephoneNumber: null,
                faxNumber: null,
                emailAddress: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
