'use strict';

angular.module('stepApp')
    .controller('VclRequisitionController',
    ['$scope', '$state', 'VclRequisition', 'VclRequisitionSearch', 'ParseLinks',
    function ($scope, $state, VclRequisition, VclRequisitionSearch, ParseLinks) {

        $scope.vclRequisitions = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            VclRequisition.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.vclRequisitions = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            VclRequisitionSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.vclRequisitions = result;
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
            $scope.vclRequisition = {
                requisitionType: null,
                vehicleType: null,
                sourceLocation: null,
                destinationLocation: null,
                expectedDate: null,
                returnDate: null,
                requisitionCause: null,
                requisitionStatus: null,
                meterReading: null,
                fuelReading: null,
                billAmount: null,
                expectedArrivalDate: null,
                comments: null,
                actionDate: null,
                actionBy: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
