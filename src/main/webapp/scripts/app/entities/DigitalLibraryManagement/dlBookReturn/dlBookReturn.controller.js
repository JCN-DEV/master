'use strict';

angular.module('stepApp')
    .controller('DlBookReturnController',
    ['$scope', '$state', '$modal', 'DlBookReturn', 'DlBookReturnSearch', 'ParseLinks','FindDlBookReturnByInstId','DlClearanceByStudentId',
    function ($scope, $state, $modal, DlBookReturn, DlBookReturnSearch, ParseLinks,FindDlBookReturnByInstId,DlClearanceByStudentId) {

        $scope.dlBookReturns = [];
        $scope.page = 0;
        $scope.currentPage = 1;
        $scope.DateForRecipt=new Date();

        $scope.count=0;
                 $scope.pageSize = 10;
        $scope.loadAll = function() {
            DlBookReturn.query({page: $scope.page, size: 1000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dlBookReturns = result;
            });
        };


        $scope.showDetail = function() {
            DlClearanceByStudentId.get({id: $scope.stuId}, function(result) {
                                   $scope.dlBookClearance = result;
                                   console.log("clear list");
                                   console.log($scope.dlBookClearance);
                               });
        };

        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            DlBookReturnSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.dlBookReturns = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

         FindDlBookReturnByInstId.query({page: $scope.page, size: 20}, function(result, headers) {

                     $scope.FindDlBookReturnByInstId = result;
                     console.log(result);

                 });

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.dlBookReturn = {
                issueId: null,
                createdDate: null,
                updatedDate: null,
                createdBy: null,
                updatedBy: null,
                status: null,
                id: null
            };
        };
    }]);
