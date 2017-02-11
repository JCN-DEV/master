'use strict';

angular.module('stepApp')
    .controller('AlmShiftSetupController',
    ['$scope', 'AlmShiftSetup', 'AlmShiftSetupSearch', 'ParseLinks',
    function ($scope, AlmShiftSetup, AlmShiftSetupSearch, ParseLinks) {
        $scope.almShiftSetups = [];
        $scope.page = 0;
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.loadAll = function() {
            AlmShiftSetup.query({page: $scope.page, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almShiftSetups = result;
              //  $scope.almShiftSetups.sort($scope.sortById)
            });
        };
        $scope.sortById = function(a,b){
            return b.id - a.id;
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AlmShiftSetup.get({id: id}, function(result) {
                $scope.almShiftSetup = result;
                $('#deleteAlmShiftSetupConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmShiftSetup.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmShiftSetupConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmShiftSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almShiftSetups = result;
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
            $scope.almShiftSetup = {
                shiftName: null,
                description: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
