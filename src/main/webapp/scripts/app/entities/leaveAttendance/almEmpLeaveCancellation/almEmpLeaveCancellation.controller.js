'use strict';

angular.module('stepApp')
    .controller('AlmEmpLeaveCancellationController',
    ['$scope', 'HrEmployeeInfo', 'AlmEmpLeaveCancellation', 'AlmEmpLeaveCancellationSearch', 'ParseLinks',
    function ($scope, HrEmployeeInfo, AlmEmpLeaveCancellation, AlmEmpLeaveCancellationSearch, ParseLinks) {
        $scope.almEmpLeaveCancellations = [];
        $scope.page = 0;
        $scope.newRequestList = [];

        HrEmployeeInfo.get({id: 'my'}, function (result) {
            $scope.hrEmployeeInfo = result;

        });


        $scope.loadAll = function()
        {
            $scope.requestEntityCounter = 1;
            $scope.newRequestList = [];
            AlmEmpLeaveCancellation.query(function(result)
            {
                $scope.requestEntityCounter++;
                angular.forEach(result,function(dtoInfo)
                {
                    if(dtoInfo.employeeInfo.id == $scope.hrEmployeeInfo.id){
                        $scope.newRequestList.push(dtoInfo);
                    }
                });
            });
            $scope.newRequestList.sort($scope.sortById);
        };

        $scope.loadAll();

        $scope.sortById = function(a,b){
            return b.id - a.id;
        };

        $scope.searchText = "";
        $scope.updateSearchText = "";

        $scope.clearSearchText = function (source)
        {
            if(source=='request')
            {
                $timeout(function(){
                    $('#searchText').val('');
                    angular.element('#searchText').triggerHandler('change');
                });
            }
        };



       /* $scope.loadAll = function() {
            AlmEmpLeaveCancellation.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almEmpLeaveCancellations = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();*/

        $scope.delete = function (id) {
            AlmEmpLeaveCancellation.get({id: id}, function(result) {
                $scope.almEmpLeaveCancellation = result;
                $('#deleteAlmEmpLeaveCancellationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmEmpLeaveCancellation.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmEmpLeaveCancellationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmEmpLeaveCancellationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almEmpLeaveCancellations = result;
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
            $scope.almEmpLeaveCancellation = {
                requestDate: null,
                requestType: null,
                cancelStatus: null,
                causeOfCancellation: null,
                activeStatus: false,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
