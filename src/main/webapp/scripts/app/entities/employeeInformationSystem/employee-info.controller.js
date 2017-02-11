'use strict';

angular.module('stepApp')
    .controller('EmployeeInfoController',
    ['$scope', '$state', '$modal', 'DataUtils', 'Principal', 'InstEmployee', 'InstEmployeeSearch', 'ParseLinks','CurrentInstEmployee','InstituteByLogin',
    function ($scope, $state, $modal, DataUtils, Principal, InstEmployee, InstEmployeeSearch, ParseLinks,CurrentInstEmployee,InstituteByLogin) {

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

           if (Principal.hasAnyAuthority(['ROLE_INSTITUTE'])) {
               InstituteByLogin.query({},function(result){
                  $scope.logInInstitute = result;
                  console.log("==================");
                  console.log($scope.logInInstitute.code);
                  console.log($scope.logInInstitute.type);
                  console.log("==================");
              });
           }

    }]).controller('InstEmployeeMpoEnlistedListController',
    ['$scope', '$location', '$state', '$modal', 'DataUtils', 'Principal', 'InstEmployee', 'InstEmployeeSearch', 'ParseLinks','CurrentInstEmployee','InstituteByLogin','AllMpoEnlistedEmployeeOfCurrent',
    function ($scope, $location, $state, $modal, DataUtils, Principal, InstEmployee, InstEmployeeSearch, ParseLinks,CurrentInstEmployee,InstituteByLogin,AllMpoEnlistedEmployeeOfCurrent) {
            console.log('I found controller!!!!!!!!!!!!!!!');
           $scope.abbreviate = DataUtils.abbreviate;
           $scope.byteSize = DataUtils.byteSize;


           /* if(Principal.hasAnyAuthority(['ROLE_INSTEMP'])) {
                AllMpoEnlistedEmployeeOfCurrent.query({},function(result){
                   $scope.CurrentInstEmployee = result;
                   console.log("====================");
                   console.log(result);
                   console.log("======================");
               });
            }*/
                console.log($location.path());
                if($location.path() == '/employee-info/mpo-enlisted-employee-list'){
                    $scope.showHeadingmpo = '/employee-info/mpo-enlisted-employee-list';
                }
                else{
                    $scope.showHeadingmpo = null;
                }

           if (Principal.hasAnyAuthority(['ROLE_INSTITUTE'])) {
               AllMpoEnlistedEmployeeOfCurrent.query({},function(result){
                  $scope.instEmployees = result;

              });
           }

    }]);
