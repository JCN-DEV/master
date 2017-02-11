'use strict';

angular.module('stepApp')
    .controller('JobPlacementInfoController',
    ['$scope', '$state', '$modal', 'JobPlacementInfo', 'JobPlacementInfoSearch', 'ParseLinks',
        function ($scope, $state, $modal, JobPlacementInfo, JobPlacementInfoSearch, ParseLinks) {

            $scope.jobPlacementInfos = [];
            $scope.page = 0;
            $scope.loadAll = function() {
                JobPlacementInfo.query({page: $scope.page, size: 1000}, function(result, headers) {
                    $scope.links = ParseLinks.parse(headers('link'));
                    $scope.jobPlacementInfos = result;
                });
            };
            $scope.loadPage = function(page) {
                $scope.page = page;
                $scope.loadAll();
            };
            $scope.loadAll();


            $scope.search = function () {
                JobPlacementInfoSearch.query({query: $scope.searchQuery}, function(result) {
                    $scope.jobPlacementInfos = result;
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
                $scope.jobPlacementInfo = {
                    jobId: null,
                    orgName: null,
                    description: null,
                    address: null,
                    designation: null,
                    department: null,
                    responsibility: null,
                    workFrom: null,
                    workTo: null,
                    currWork: null,
                    experience: null,
                    createDate: null,
                    createBy: null,
                    updateBy: null,
                    updateDate: null,
                    id: null
                };
            };
        }]);
