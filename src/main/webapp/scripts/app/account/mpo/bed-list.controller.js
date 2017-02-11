
        'use strict';

        angular.module('stepApp')
            .controller('BEdListController',
             ['$scope', 'entity', '$state','BEdApplication', '$modal', 'TimeScaleApplication', 'TimescaleApplicationList', 'MpoApplication', 'Employee', 'Institute' ,'EmployeeSearch', 'EmployeeInstitute', 'ParseLinks', 'MpoApplicationLogEmployeeCode' ,'MpoApplicationInstitute','BEdApplicationList','Principal','InstLevelByName','BEdSummaryList','BEdApplicationsByLevel',
             function ($scope, entity, $state,BEdApplication, $modal, TimeScaleApplication, TimescaleApplicationList, MpoApplication, Employee, Institute ,EmployeeSearch, EmployeeInstitute, ParseLinks, MpoApplicationLogEmployeeCode , MpoApplicationInstitute,BEdApplicationList,Principal,InstLevelByName,BEdSummaryList,BEdApplicationsByLevel) {

                $scope.timescaleAppliedLists = entity;
                $scope.summaryList = [];
                $scope.employees = [];
                $scope.currentPage = 1;
                $scope.pageSize = 10;
                $scope.page = 0;
                BEdApplicationList.query({status:1,page: $scope.page, size: 100}, function(result, headers) {
                    // $scope.pendingApplications = result;
                    console.log(result);
                });
                BEdApplicationList.query({status:0,page: $scope.page, size: 100}, function(result, headers) {
                    // $scope.pendingApplications = result;
                    console.log(result);
                });
                BEdApplication.query({page: $scope.page, size: 100}, function(result, headers) {
                    $scope.mpoApplications = result;
                    //  console.log($scope.mpoApplications);
                });

                 if(entity.pending === "pending" && (Principal.hasAnyAuthority(['ROLE_DIRECTOR']) || Principal.hasAnyAuthority(['ROLE_DG']))){
                     BEdSummaryList.query({}, function(result){
                         $scope.summaryList = result;
                     });
                 }


                 $scope.createSummary = function () {
                     console.log('create summary method called');
                     BEdSummaryList.query({}, function(result){
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
                         BEdApplicationsByLevel.query({status: 0, levelId: result.id}, function(result2){
                             $scope.timescaleAppliedLists = result2;
                         });
                     });

                 };
                 $scope.hsc = function () {
                     InstLevelByName.get({name: "HSC (BM)"}, function(result){
                         BEdApplicationsByLevel.query({status: 0, levelId: result.id}, function(result2){
                             $scope.timescaleAppliedLists = result2;
                         });
                     });
                 };
                 $scope.mdvoc = function () {
                     InstLevelByName.get({name: "Madrasha (VOC)"}, function(result){
                         BEdApplicationsByLevel.query({status: 0, levelId: result.id}, function(result2){
                             $scope.timescaleAppliedLists = result2;
                         });
                     });
                 };
                 $scope.mdbm = function () {
                     InstLevelByName.get({name: "Madrasha (BM)"}, function(result){
                         BEdApplicationsByLevel.query({status: 0, levelId: result.id}, function(result2){
                             $scope.timescaleAppliedLists = result2;
                         });
                     });
                 };
                 var onForwardSuccess = function (index) {

                 };

                 $scope.forwardSummaryList = function () {
                     angular.forEach($scope.summaryList, function (data) {
                         BEdApplication.get({id: data.MPO_ID}, function(result){
                             result.adForwarded = true;
                             console.log(result);
                             BEdApplication.update(result);
                         });
                         // location.reload();
                     });
                     location.reload();
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

