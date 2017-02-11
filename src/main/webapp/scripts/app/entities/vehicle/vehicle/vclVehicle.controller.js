'use strict';

angular.module('stepApp')

    .controller('VclVehicleController',
    ['$scope', '$state', 'VclVehicle', 'VclVehicleSearch', 'ParseLinks',
    function ($scope, $state, VclVehicle, VclVehicleSearch, ParseLinks) {

        $scope.vclVehicles = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            VclVehicle.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.vclVehicles = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            VclVehicleSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.vclVehicles = result;
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
            $scope.vclVehicle = {
                vehicleName: null,
                vehicleNumber: null,
                vehicleType: null,
                licenseNumber: null,
                chassisNumber: null,
                dateOfBuying: null,
                supplierName: null,
                noOfSeats: null,
                passengerCapacity: null,
                vehicleAvailability: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
