
        'use strict';

        angular.module('stepApp')
            .controller('TimescaleListController',
            ['$scope', 'entity', '$state', '$modal', 'TimeScaleApplication', 'TimescaleApplicationList', 'MpoApplication', 'Employee', 'Institute' ,'EmployeeSearch', 'EmployeeInstitute', 'ParseLinks', 'MpoApplicationLogEmployeeCode' , 'MpoApplicationInstitute','MpoApplicationList','TimescaleSummaryList','Principal','InstLevelByName','TimescaleApplicationsByLevel',
            function ($scope, entity, $state, $modal, TimeScaleApplication, TimescaleApplicationList, MpoApplication, Employee, Institute ,EmployeeSearch, EmployeeInstitute, ParseLinks, MpoApplicationLogEmployeeCode , MpoApplicationInstitute,MpoApplicationList,TimescaleSummaryList,Principal,InstLevelByName,TimescaleApplicationsByLevel) {

                $scope.timescaleAppliedLists = entity;
                $scope.summaryList = [];
                $scope.employees = [];
                $scope.page = 0;
                $scope.currentPage = 1;
                $scope.pageSize = 10;

                if(entity.pending === "pending" && (Principal.hasAnyAuthority(['ROLE_DIRECTOR']) || Principal.hasAnyAuthority(['ROLE_DG']))){
                    TimescaleSummaryList.query({}, function(result){
                        console.log("comes to summarylist");
                        console.log(result);
                        $scope.summaryList = result;
                    });
                }

                /*TimescaleApplicationList.query({status:1,page: $scope.page, size: 100}, function(result, headers) {
                    // $scope.pendingApplications = result;
                    console.log(result);
                });
                TimescaleApplicationList.query({status:0,page: $scope.page, size: 100}, function(result, headers) {
                    $scope.timescaleAppliedLists = result;
                    console.log(result);
                });*/
                TimeScaleApplication.query({page: $scope.page, size: 100}, function(result, headers) {
                    $scope.mpoApplications = result;
                    //  console.log($scope.mpoApplications);
                });

                $scope.createSummary = function () {
                    TimescaleSummaryList.query({}, function(result){
                        $scope.summaryList = result;
                    });
                };

                $scope.all = function () {
                    $scope.timescaleAppliedLists = entity;
                    /* MpoApplicationsByLevel.query({status: 0, levelId: 8550151454}, function(result){
                     $scope.mpoAppliedLists = entity;
                     });*/
                };
                $scope.ssc = function () {
                    InstLevelByName.get({name: "SSC (VOC)"}, function(result){
                        TimescaleApplicationsByLevel.query({status: 0, levelId: result.id}, function(result2){
                            $scope.timescaleAppliedLists = result2;
                        });
                    });

                };
                $scope.hsc = function () {
                    InstLevelByName.get({name: "HSC (BM)"}, function(result){
                        TimescaleApplicationsByLevel.query({status: 0, levelId: result.id}, function(result2){
                            $scope.timescaleAppliedLists = result2;
                        });
                    });
                };
                $scope.mdvoc = function () {
                    InstLevelByName.get({name: "Madrasha (VOC)"}, function(result){
                        TimescaleApplicationsByLevel.query({status: 0, levelId: result.id}, function(result2){
                            $scope.timescaleAppliedLists = result2;
                        });
                    });
                };
                $scope.mdbm = function () {
                    InstLevelByName.get({name: "Madrasha (BM)"}, function(result){
                        TimescaleApplicationsByLevel.query({status: 0, levelId: result.id}, function(result2){
                            $scope.timescaleAppliedLists = result2;
                        });
                    });
                };
                var onForwardSuccess = function (index) {

                };

                $scope.forwardSummaryList = function () {
                    angular.forEach($scope.summaryList, function (data) {
                        TimeScaleApplication.get({id: data.MPO_ID}, function(result){
                            result.adForwarded = true;
                            console.log(result);
                            TimeScaleApplication.update(result);
                        });
                        // location.reload();
                    });

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

