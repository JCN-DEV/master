'use strict';

angular.module('stepApp')
    .controller('EmployeeDashboardController',
    ['$scope', '$state','Principal', '$modal', 'DataUtils', 'InstEmployee', 'InstEmployeeSearch', 'ParseLinks','CurrentInstEmployee','InstituteByLogin',
    function ($scope, $state,Principal, $modal, DataUtils, InstEmployee, InstEmployeeSearch, ParseLinks,CurrentInstEmployee,InstituteByLogin) {

           $scope.abbreviate = DataUtils.abbreviate;
           $scope.byteSize = DataUtils.byteSize;
            if(Principal.hasAnyAuthority(['ROLE_INSTEMP'])) {
                CurrentInstEmployee.get({},function(result){
                   $scope.CurrentInstEmployee = result;
                   console.log("====================");
                   console.log(result);
                   console.log("======================");
               });
            }

    }]);
