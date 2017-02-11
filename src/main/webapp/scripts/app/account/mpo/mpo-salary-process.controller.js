'use strict';

angular.module('stepApp')
    .controller('MpoSalaryProcessController',
    ['$scope', '$state', 'Principal','MpoSalaryMonthList','MpoSalaryYearList','SalaryGeneratedForYearAndMonth','$rootScope',
    function ($scope, $state, Principal,MpoSalaryMonthList,MpoSalaryYearList,SalaryGeneratedForYearAndMonth,$rootScope) {

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
            SalaryGeneratedForYearAndMonth.get({year: val,'month': val2},function(result){
                console.log(result);
                //$('#confirmationProcess').modal('show');
                //alert('pop');
                $rootScope.confirmationObject.pageTitle = 'Process Confirmation';
                $rootScope.confirmationObject.pageTexts = 'Requested Salary Process Executed';
                $rootScope.showConfirmation();
            });
        }

        //$scope.clear = function() {
        //    $('#confirmationProcess').modal('hide');
        //    $state.go('mpo.processSalary');
        //};

    }]);
