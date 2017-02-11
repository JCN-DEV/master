'use strict';

angular.module('stepApp')
    .controller('MpoFinalListToApprove',
    ['$scope', '$state', 'MpoApplicationFinalList', '$modal',  'MpoApplication', 'Employee', 'Institute' ,'EmployeeSearch', 'EmployeeInstitute', 'ParseLinks', 'MpoApplicationInstitute','MpoApplicationList',
    function ($scope, $state, MpoApplicationFinalList, $modal,  MpoApplication, Employee, Institute ,EmployeeSearch, EmployeeInstitute, ParseLinks, MpoApplicationInstitute,MpoApplicationList) {

        $scope.page = 0;
        $scope.mpoApplications = MpoApplicationFinalList.query();


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

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

    }]);
