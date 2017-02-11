'use strict';

angular.module('stepApp')
    .controller('HrGazetteSetupController',
     ['$scope', '$state', 'HrGazetteSetup','HrGazetteSetupSearch', 'ParseLinks',
     function ($scope, $state, HrGazetteSetup, HrGazetteSetupSearch, ParseLinks) {

        $scope.hrGazetteSetups = [];
        $scope.predicate = 'gazetteName';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            HrGazetteSetup.query({page: $scope.page - 1, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrGazetteSetups = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            HrGazetteSetupSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrGazetteSetups = result;
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
            $scope.hrGazetteSetup = {
                gazetteCode: null,
                gazetteName: null,
                gazetteYear: null,
                gazetteDetail: null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
