'use strict';

angular.module('stepApp')
    .controller('EmployeeController',
    ['$scope', '$state', '$modal', 'Employee', 'EmployeeSearch', 'ParseLinks',
    function ($scope, $state, $modal, Employee, EmployeeSearch, ParseLinks) {

        $scope.employees = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Employee.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.employees = result;
                $scope.total = headers('x-total-count');
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.loadAll();

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

        $scope.clear = function () {
            $scope.employee = {
                name: null,
                nameEnglish: null,
                father: null,
                mother: null,
                presentAddress: null,
                permanentAddress: null,
                gender: null,
                dob: null,
                zipCode: null,
                registrationCertificateSubject: null,
                registrationExamYear: null,
                registrationCetificateNo: null,
                indexNo: null,
                bankName: null,
                bankBranch: null,
                bankAccountNo: null,
                designation: null,
                subject: null,
                salaryScale: null,
                salaryCode: null,
                monthlySalaryGovtProvided: null,
                monthlySalaryInstituteProvided: null,
                gbResolutionReceiveDate: null,
                gbResolutionAgendaNo: null,
                circularPaperName: null,
                circularPublishedDate: null,
                recruitExamReceiveDate: null,
                dgRepresentativeName: null,
                dgRepresentativeDesignation: null,
                dgRepresentativeAddress: null,
                boardRepresentativeName: null,
                boardRepresentativeDesignation: null,
                boardRepresentativeAddress: null,
                recruitApproveGBResolutionDate: null,
                recruitPermitAgendaNo: null,
                recruitmentDate: null,
                presentInstituteJoinDate: null,
                presentPostJoinDate: null,
                timeScaleGBResolutionDate: null,
                totalJobDuration: null,
                currentPositionJobDuration: null,
                id: null
            };
        };

        // bulk operations start
        $scope.areAllEmployeesSelected = false;

        $scope.updateEmployeesSelection = function (employeeArray, selectionValue) {
            for (var i = 0; i < employeeArray.length; i++)
            {
            employeeArray[i].isSelected = selectionValue;
            }
        };


        $scope.import = function (){
            for (var i = 0; i < $scope.employees.length; i++){
                var employee = $scope.employees[i];
                if(employee.isSelected){
                    //Employee.update(employee);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.export = function (){
            for (var i = 0; i < $scope.employees.length; i++){
                var employee = $scope.employees[i];
                if(employee.isSelected){
                    //Employee.update(employee);
                    //TODO: handle bulk export
                }
            }
        };

        $scope.deleteSelected = function (){
            for (var i = 0; i < $scope.employees.length; i++){
                var employee = $scope.employees[i];
                if(employee.isSelected){
                    Employee.delete(employee);
                }
            }
        };

        $scope.sync = function (){
            for (var i = 0; i < $scope.employees.length; i++){
                var employee = $scope.employees[i];
                if(employee.isSelected){
                    Employee.update(employee);
                }
            }
        };

        $scope.order = function (predicate, reverse) {
            $scope.predicate = predicate;
            $scope.reverse = reverse;
            Employee.query({page: $scope.page, size: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.employees = result;
                $scope.total = headers('x-total-count');
            });
        };
        // bulk operations end

    }]);
