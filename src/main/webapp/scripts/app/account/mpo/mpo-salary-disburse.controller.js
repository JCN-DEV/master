'use strict';

angular.module('stepApp')
    .controller('MpoSalaryDisburseController',
    ['$scope', '$state', 'Principal','MpoSalaryMonthList','MpoSalaryYearList','SalaryDisburseForYearAndMonth','$rootScope',
    function ($scope, $state, Principal,MpoSalaryMonthList,MpoSalaryYearList,SalaryDisburseForYearAndMonth,$rootScope) {

        /*if(Principal.hasAnyAuthority(['ROLE_INSTEMP'])){

        }*/
        // bulk operations end


        $scope.yearList=MpoSalaryYearList.get({},function(result){
            console.log(result);
        });
        $scope.monthList=MpoSalaryMonthList.get({});



        $scope.checkMonthAndSalary = function(val,val2)
        {
            console.log(val);
            console.log(val2);
            SalaryDisburseForYearAndMonth.get({year: val,'month': val2},function(result){
                console.log(result);
                //$('#confirmationProcess').modal('show');
                //alert('pop');
                $rootScope.confirmationObject.pageTitle = 'Disburse Confirmation';
                $rootScope.confirmationObject.pageTexts = 'Requested Salary Disburse Executed';
                $rootScope.showConfirmation();
            });
        }

        //$scope.clear = function() {
        //    $('#confirmationProcess').modal('hide');
        //    $state.go('mpo.processSalary');
        //};

    }]);
