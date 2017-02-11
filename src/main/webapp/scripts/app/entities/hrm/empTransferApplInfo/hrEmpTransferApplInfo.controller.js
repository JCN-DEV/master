'use strict';

angular.module('stepApp')
    .controller('HrEmpTransferApplInfoController',
     ['$scope', '$state', 'HrEmpTransferApplInfo', 'HrEmpTransferApplInfoSearch', 'ParseLinks',
     function ($scope, $state, HrEmpTransferApplInfo, HrEmpTransferApplInfoSearch, ParseLinks) {

        $scope.hrEmpTransferApplInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            HrEmpTransferApplInfo.query({page: $scope.page - 1, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrEmpTransferApplInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            HrEmpTransferApplInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrEmpTransferApplInfos = result;
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
            $scope.hrEmpTransferApplInfo = {
                transferReason: null,
                officeOrderNumber: null,
                officeOrderDate: null,
                activeStatus: true,
                selectedOption: null,
                logId: null,
                logStatus: null,
                logComments: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
