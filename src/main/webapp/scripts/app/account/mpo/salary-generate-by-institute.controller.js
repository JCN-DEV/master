'use strict';

angular.module('stepApp')
    .controller('SalaryGenerateByInstituteController',
    ['$scope', 'entity', '$state', 'SalaryGenerateByInstitute', 'InstEmployee', 'MpoApplication', 'PayScale',
    function ($scope, entity, $state, SalaryGenerateByInstitute, InstEmployee, MpoApplication, PayScale) {

        //$scope.salaryReports = entity;

        $scope.salaryReports = SalaryGenerateByInstitute.query(function(response){
            console.log(response);
        });

        console.log($scope.salaryReports);

        $scope.years = [
            {'key' : '2015', 'value' : '2015'},
            {'key' : '2016', 'value' : '2016'},
            {'key' : '2017', 'value' : '2017'},
            {'key' : '2018', 'value' : '2018'}
        ];

        $scope.months = [
            {'key' : '01', 'value' : 'January'},
            {'key' : '02', 'value' : 'February'},
            {'key' : '03', 'value' : 'March'},
            {'key' : '04', 'value' : 'April'},
            {'key' : '05', 'value' : 'May'},
            {'key' : '06', 'value' : 'June'},
            {'key' : '07', 'value' : 'July'},
            {'key' : '08', 'value' : 'August'},
            {'key' : '09', 'value' : 'September'},
            {'key' : '10', 'value' : 'October'},
            {'key' : '11', 'value' : 'Nobember'},
            {'key' : '12', 'value' : 'December'}
        ];

    }]);
