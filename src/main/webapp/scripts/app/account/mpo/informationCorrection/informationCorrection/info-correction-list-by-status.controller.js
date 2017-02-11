'use strict';

angular.module('stepApp')
    .controller('InfoCorrectListByStatusController',
    ['$scope', 'entity', '$state', '$modal', 'Employee', 'Institute' ,'EmployeeSearch', 'EmployeeInstitute', 'ParseLinks' ,'Principal',
    function ($scope, entity, $state, $modal, Employee, Institute ,EmployeeSearch, EmployeeInstitute, ParseLinks ,Principal) {

        $scope.mpoAppliedLists = entity;
        $scope.allApplications = entity;
       /* MpoCommitteeDescisionByLogin.query({}, function(result){
            if(result.length >0){
                console.log(result[0].mpoApplicationId);
            }
        });*/

        /*MpoApplicationList.query({status:1,page: $scope.page, size: 100}, function(result, headers) {
           // $scope.pendingApplications = result;
            console.log(result);
        });
        MpoApplicationList.query({status:0,page: $scope.page, size: 100}, function(result, headers) {
            // $scope.pendingApplications = result;
            console.log(result);
        });*/

       /* if(entity.pending === "pending" && (Principal.hasAnyAuthority(['ROLE_DIRECTOR']) || Principal.hasAnyAuthority(['ROLE_DG']))){
            MpoApplicationSummaryList.query({}, function(result){
                $scope.summaryList = result;
            });
        }*/
        /*MpoApplication.query({page: $scope.page, size: 100}, function(result, headers) {
            $scope.mpoApplications = result;
          //  console.log($scope.mpoApplications);
        });
*/
        /*$scope.createSummary = function () {
            MpoApplicationSummaryList.query({}, function(result){
                $scope.summaryList = result;
            });
        };*/

       /* $scope.all = function () {
            $scope.mpoAppliedLists = entity;
           *//* MpoApplicationsByLevel.query({status: 0, levelId: 8550151454}, function(result){
                $scope.mpoAppliedLists = entity;
            });*//*
        };*/

        var onForwardSuccess = function (index) {

        };

        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.search = function () {
            EmployeeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.employees = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

    }]);
