'use strict';

angular.module('stepApp')
    .controller('VclDriverController',
    ['$scope', '$state', 'DataUtils', 'VclDriver', 'VclDriverSearch', 'ParseLinks',
    function ($scope, $state, DataUtils, VclDriver, VclDriverSearch, ParseLinks) {

        $scope.vclDrivers = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            VclDriver.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.vclDrivers = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            VclDriverSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.vclDrivers = result;
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
            $scope.vclDriver = {
                driverName: null,
                licenseNumber: null,
                presentAddress: null,
                permanentAddress: null,
                mobileNumber: null,
                emergencyNumber: null,
                joinDate: null,
                retirementDate: null,
                photoName: null,
                driverPhoto: null,
                driverPhotoContentType: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    }]);
