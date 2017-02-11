'use strict';

angular.module('stepApp')
    .controller('PayScaleListByStatusController',
     ['$scope', 'entity', '$state', '$modal',  'Principal','MpoApplication', 'MpoApplicationPayScale',
     function ($scope, entity, $state, $modal,  Principal,MpoApplication, MpoApplicationPayScale) {

        $scope.payScalePendingLists = entity;

        console.log($scope.payScalePendingLists);

        $scope.employees = [];
        $scope.page = 0;
        if(Principal.hasAuthority("ROLE_MPOADMIN")){
        console.log("-----------------------"+true);
        }else{
            console.log("-----------------------"+true);
        }

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
        // bulk operations end

    }]);
