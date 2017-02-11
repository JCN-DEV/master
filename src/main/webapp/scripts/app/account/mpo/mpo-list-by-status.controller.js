'use strict';

angular.module('stepApp')
    .controller('MpoListByStatus',
    ['$scope', 'entity', '$state', '$modal','MpoCommitteeDescisionByLogin',  'MpoApplication', 'Employee', 'Institute' ,'EmployeeSearch', 'EmployeeInstitute', 'ParseLinks', 'MpoApplicationLogEmployeeCode' , 'MpoApplicationInstitute','MpoApplicationList','MpoApplicationSummaryList','Principal','MpoApplicationsByLevel','InstLevelByName', '$rootScope',
    function ($scope, entity, $state, $modal,MpoCommitteeDescisionByLogin,  MpoApplication, Employee, Institute ,EmployeeSearch, EmployeeInstitute, ParseLinks, MpoApplicationLogEmployeeCode , MpoApplicationInstitute,MpoApplicationList,MpoApplicationSummaryList,Principal,MpoApplicationsByLevel,InstLevelByName, $rootScope) {

        $scope.mpoAppliedLists = entity;
        $scope.allApplications = entity;
        $scope.showSummary = false;
        $scope.currentPage = 1;
        $scope.pageSize = 10;

        $scope.employees = [];
        $scope.summaryList = [];
        $scope.page = 0;
        console.log('printing status:'+entity.pending);
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

        if(entity.pending === "pending" && (Principal.hasAnyAuthority(['ROLE_DIRECTOR']) || Principal.hasAnyAuthority(['ROLE_DG']))){
            MpoApplicationSummaryList.query({}, function(result){
                $scope.summaryList = result;
            });
        }
        MpoApplication.query({page: $scope.page, size: 100}, function(result, headers) {
            $scope.mpoApplications = result;
          //  console.log($scope.mpoApplications);
        });

        $scope.createSummary = function () {
            MpoApplicationSummaryList.query({}, function(result){
                $scope.summaryList = result;
                if(result.length == 0){
                    alert("No application found!");
                }
                console.log("result found");
            }, function(response) {
                if(response.status === 404) {
                    alert("No application found!");
                }
            });
        };

        $scope.all = function () {
            $scope.showSummary = false;
            $scope.mpoAppliedLists = entity;
           /* MpoApplicationsByLevel.query({status: 0, levelId: 8550151454}, function(result){
                $scope.mpoAppliedLists = entity;
            });*/
        };
        $scope.ssc = function () {
            $scope.showSummary = false;
            InstLevelByName.get({name: "SSC (VOC)"}, function(result){
                MpoApplicationsByLevel.query({status: 0, levelId: result.id}, function(result2){
                    $scope.mpoAppliedLists = result2;
                });
            });

        };
        $scope.hsc = function () {
            $scope.showSummary = false;
            InstLevelByName.get({name: "HSC (BM)"}, function(result){
                MpoApplicationsByLevel.query({status: 0, levelId: result.id}, function(result2){
                    $scope.mpoAppliedLists = result2;
                });
            });
        };
        $scope.mdvoc = function () {
            $scope.showSummary = false;
            InstLevelByName.get({name: "Madrasha (VOC)"}, function(result){
                MpoApplicationsByLevel.query({status: 0, levelId: result.id}, function(result2){
                    $scope.mpoAppliedLists = result2;
                });
            });
        };
        $scope.mdbm = function () {
            $scope.showSummary = false;
            InstLevelByName.get({name: "Madrasha (BM)"}, function(result){
                MpoApplicationsByLevel.query({status: 0, levelId: result.id}, function(result2){
                    $scope.mpoAppliedLists = result2;
                });
            });
        };

        var onForwardSuccess = function (index) {

        };
        $scope.showSummary1 = function () {
            console.log('showSummary called');
            $scope.showSummary = true;
        };
        $scope.forwardSummaryList = function () {
            angular.forEach($scope.summaryList, function (data) {
                console.log('klasdfkljsdfj');
                console.log(data.MPO_ID);
                MpoApplication.get({id: data.MPO_ID}, function(result){
                    console.log(result);
                    result.adForwarded = true;
                    console.log(result);
                    MpoApplication.update(result);
                    //MpoApplication.update(result);
                });
               // location.reload();
            });

            $rootScope.setSuccessMessage('Applications Forwarded Successfully.');
            location.reload();
            //$state.go('mpo.details', null, { reload: true });

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
