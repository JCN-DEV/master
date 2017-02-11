'use strict';

angular.module('stepApp')
    .controller('DlBookIssueController',
    ['$scope', '$state', '$modal', 'DlBookIssue', 'DlBookIssueSearch', 'ParseLinks','FindDlBookIssueByInstId','FindAllByUserRequisition','findAllBookIssue',
     function ($scope, $state, $modal, DlBookIssue, DlBookIssueSearch, ParseLinks,FindDlBookIssueByInstId,FindAllByUserRequisition,findAllBookIssue) {

        $scope.dlBookIssues = [];
        $scope.page = 0;
        $scope.currentPage = 1;
                 $scope.pageSize = 10;
        $scope.loadAll = function() {
            DlBookIssue.query({page: $scope.page, size: 1000}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dlBookIssues = result;
            });
        };


         FindAllByUserRequisition.query(
         {}, function(result) {
                        $scope.dlRequisitionsbyUser = result;
                      console.log("Requisition list");
                      console.log( $scope.dlRequisitionsbyUser);


                    }
                    );
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            DlBookIssueSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.dlBookIssues = result;
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

         FindDlBookIssueByInstId.query({page: $scope.page, size: 20}, function(result, headers) {

             $scope.FindDlBookIssueByInstId = result;
             console.log(result);

         });


        $scope.clear = function () {
            $scope.dlBookIssue = {
                isbnNo: null,
                noOfCopies: null,
                returnDate: null,
                createdDate: null,
                updatedDate: null,
                createdBy: null,
                updatedBy: null,
                status: null,
                pStatus: null,
                id: null
            };
        };
    }]);
