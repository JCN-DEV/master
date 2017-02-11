'use strict';

angular.module('stepApp')
    .controller('HrProjectInfoController',
    ['$scope', '$state', 'HrProjectInfo', 'HrProjectInfoSearch', 'ParseLinks',
    function ($scope, $state, HrProjectInfo, HrProjectInfoSearch, ParseLinks) {

        $scope.hrProjectInfos = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            HrProjectInfo.query({page: $scope.page - 1, size: 5000, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.hrProjectInfos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            HrProjectInfoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.hrProjectInfos = result;
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
            $scope.hrProjectInfo = {
                projectName: null,
                projectDetail: null,
                directorName: null,
                startDate: null,
                endDate: null,
                projectValue: null,
                projectStatus: null,
                activeStatus: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };
    }]);
