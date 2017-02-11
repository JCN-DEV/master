'use strict';

angular.module('stepApp')
    .controller('MpoListCommittee',
    ['$scope', 'entity', '$state', '$modal','MpoCommitteeDescisionByLogin','MpoApplicationPendingForCommittee',  'MpoApplication', 'Employee', 'Institute' ,'EmployeeSearch', 'EmployeeInstitute', 'ParseLinks', 'MpoApplicationLogEmployeeCode' , 'MpoApplicationInstitute','MpoApplicationList',
    function ($scope, entity, $state, $modal,MpoCommitteeDescisionByLogin,MpoApplicationPendingForCommittee,  MpoApplication, Employee, Institute ,EmployeeSearch, EmployeeInstitute, ParseLinks, MpoApplicationLogEmployeeCode , MpoApplicationInstitute,MpoApplicationList) {

       // $scope.mpoAppliedLists = entity;

        $scope.employees = [];
        $scope.page = 0;
        MpoApplicationPendingForCommittee.query({}, function(result){
            console.log('committee result found');
            console.log(result.length);
            $scope.mpoAppliedLists = result;
        });
        MpoCommitteeDescisionByLogin.query({}, function(result){
            console.log(result[0].mpoApplicationId);
        });
        /*MpoApplicationList.query({status:1,page: $scope.page, size: 100}, function(result, headers) {
           // $scope.pendingApplications = result;
            console.log(result);
        });
        MpoApplicationList.query({status:0,page: $scope.page, size: 100}, function(result, headers) {
            // $scope.pendingApplications = result;
            console.log(result);
        });
        MpoApplication.query({page: $scope.page, size: 100}, function(result, headers) {
            $scope.mpoApplications = result;
          //  console.log($scope.mpoApplications);
        });

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
                payScale: null,
                payScaleCode: null,
                month: null,
                monthlySalaryGovtProvided: null,
                monthlySalaryInstituteProvided: null,
                gbResolutionReceiveDate: null,
                gbResolutionAgendaNo: null,
                circularPaperName: null,
                circularPublishedDate: null,
                recruitExamDate: null,
                recruitApproveGBResolutionDate: null,
                recruitPermitAgendaNo: null,
                recruitmentDate: null,
                presentInstituteJoinDate: null,
                presentPostJoinDate: null,
                dgRepresentativeType: null,
                dgRepresentativeName: null,
                dgRepresentativeDesignation: null,
                dgRepresentativeAddress: null,
                representativeType: null,
                boardRepresentativeName: null,
                boardRepresentativeDesignation: null,
                boardRepresentativeAddress: null,
                salaryCode: null,
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
        // bulk operations end*/

    }]);