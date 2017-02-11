'use strict';

angular.module('stepApp')
    .controller('AlmEarningFrequencyController',
    ['$scope', 'AlmEarningFrequency', 'AlmEarningFrequencySearch', 'ParseLinks',
    function ($scope, AlmEarningFrequency, AlmEarningFrequencySearch, ParseLinks) {
        $scope.almEarningFrequencys = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            AlmEarningFrequency.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.almEarningFrequencys = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AlmEarningFrequency.get({id: id}, function(result) {
                $scope.almEarningFrequency = result;
                $('#deleteAlmEarningFrequencyConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AlmEarningFrequency.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAlmEarningFrequencyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            AlmEarningFrequencySearch.query({query: $scope.searchQuery}, function(result) {
                $scope.almEarningFrequencys = result;
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
            $scope.almEarningFrequency = {
                earningFrequencyName: null,
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
