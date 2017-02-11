'use strict';

angular.module('stepApp')
    .controller('reportController',
    ['$scope', '$rootScope', '$stateParams', '$sce', '$state', 'DataUtils', 'ParseLinks', 'Division', 'District', 'Upazila', 'Institute', 'JasperReport', 'GetJasperParamByJasperReport', 'GetAllJasperReportByModule', 'MpoSalaryFlow',
        function ($scope, $rootScope, $stateParams, $sce, $state, DataUtils, ParseLinks, Division, District, Upazila, Institute, JasperReport, GetJasperParamByJasperReport, GetAllJasperReportByModule, MpoSalaryFlow) {

            $scope.moduleName = $stateParams.module;
            $scope.jasperReports = [];
            $scope.mpoSalaryFlow = {};
            $scope.reportName = '';


            $scope.selectedValue=function(data){
                console.log('................data: ');
                console.log(data);
                console.log(data.name);


            };

            $scope.monthNames=[
                {id:1, name: 'January'},
                {id:2, name: 'February'},
                {id:3, name: 'March'},
                {id:4, name: 'April'},
                {id:5, name: 'May'},
                {id:6, name: 'June'},
                {id:7, name: 'July'},
                {id:8, name: 'August'},
                {id:9, name: 'September'},
                {id:10, name: 'October'},
                {id:11, name: 'November'},
                {id:12, name: 'December'}
            ];

            $scope.theYears=[
                {id:1, name: 2016},
                {id:2, name: 2015},
                {id:3, name: 2014},
                {id:4, name: 2013},
                {id:5, name: 2012},
                {id:6, name: 2011},
                {id:7, name: 2010},
                {id:8, name: 2009},
                {id:9, name: 2008},
                {id:10, name: 2007},
                {id:11, name: 2006},
                {id:12, name: 2005}
            ];

            var loadModule = function () {
                GetAllJasperReportByModule.query({module: $scope.moduleName}, function (result) {
                        $scope.jasperReports = result;
                    },
                    function (response) {

                    }
                );
            };
            loadModule();
            $scope.calendar = {
                opened: {},
                dateFormat: 'yyyy-MM-dd',
                dateOptions: {},
                open: function ($event, which) {
                    $event.preventDefault();
                    $event.stopPropagation();
                    $scope.calendar.opened[which] = true;
                }
            };
            $scope.institutes=[];
            $scope.institutes=Institute.query({size: 500});
            $scope.divisions = Division.query({size: 10});
            var allDistrict = District.query({page: $scope.page, size: 65}, function (result, headers) {
                return result;
            });
            var allUpazila = Upazila.query({page: $scope.page, size: 500}, function (result, headers) {
                return result;
            });
            $scope.updatedDistrict = function (select) {
                $scope.districts = [];
                angular.forEach(allDistrict, function (district) {
                    if (select == district.division.name) {
                        $scope.districts.push(district);
                    }
                });
            };
            $scope.updatedUpazila = function (select) {

                $scope.upazilas = [];
                angular.forEach(allUpazila, function (upazila) {
                    if (select == upazila.district.name) {
                        $scope.upazilas.push(upazila);
                    }
                });
            };

            $scope.jsRport;
            $scope.rptParamDiv;
            $scope.rptBaseurl = "";
            $scope.url = "";
            $scope.reportChange = function (jasperReportName) {
                $scope.obj = $scope.jasperReport.id;
                $scope.reportName = $scope.jasperReport.id.name;
                $scope.jasperReportParameters = [];
                GetJasperParamByJasperReport.query({id: $scope.obj.id}, function (result) {
                    $scope.jasperReportParameters = result;
                });
            };
            $scope.reportPreview = function () {
                $scope.obj = $scope.jasperReport.id;
                var parmavar = "";
                var urlString = "";
                //http://202.4.121.77:9090/jasperserver/flow.html?
                //// _flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2FJP_Job_Type_Wise_Report&standAlone=true

                urlString = "http://202.4.121.77:9090/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2F";
                urlString = urlString + $scope.obj.path + "&standAlone=true&j_username=user&j_password=" + $rootScope.encCre + "&decorate=no";


                var parmavar = "";
                angular.forEach($scope.jasperReportParameters, function (data) {
                    parmavar = parmavar + "&" + data.name + "=";
                    if (data.actiontype) {
                        if (data.type == 'combo') {
                            //var array = $.map(data.actiontype, function (value, index) {
                            //    return [value];
                            //});
                            //if (array.length > 0) {
                            //    parmavar = parmavar + array[1];
                            //}
                            //parmavar = parmavar+data.actiontype;
                            //parmavar = parmavar + "%25";
                            if (data.actiontype.toString().trim() != "")
                                parmavar = parmavar + data.actiontype;
                            parmavar = parmavar + "%25";
                        }
                        else if (data.type == 'text') {
                            if (data.actiontype.toString().trim() != "")
                                parmavar = parmavar + data.actiontype;
                            parmavar = parmavar + "%25";
                        }
                        else if (data.type == 'month' || data.type == 'year') {

                            if (data.actiontype.toString().trim() != "")
                                parmavar = parmavar + data.actiontype;
                        }
                    }
                    else {
                        parmavar = parmavar + "%25";
                    }
                });
                $scope.rptBaseurl = urlString + parmavar;
                console.log($scope.rptBaseurl);
                $scope.url = $sce.trustAsResourceUrl($scope.rptBaseurl);


            };

            $scope.reportPreviewDte16 = function (requestId) {

                if(requestId == 1){
                    window.open("http://202.4.121.77:9090/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2FPay_Code_Wise_ManPower&standAlone=true&j_username=user&j_password=jasperuser&decorate=no");
                } else if(requestId == 2){
                    window.open("http://202.4.121.77:9090/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2FMPO_Sheet&standAlone=true&j_username=user&j_password=jasperuser&decorate=no&paramMonth=August%25&paramYear=2015%25&paramInstCode=%25&paramEmpIndex=%25&paramDivision=%25&paramDistrict=%25&paramThana=%25");
                } else if(requestId == 3){
                     window.open("http://202.4.121.77:9090/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2Fbank_wise_summery&standAlone=true&j_username=user&j_password=jasperuser&decorate=no&paramMonth=August%25&paramYear=2015%25");
                } else if(requestId == 4){
                     window.open("http://202.4.121.77:9090/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2FVoucher_Sheet&standAlone=true&j_username=user&j_password=jasperuser&decorate=no&paramMonth=August%25&paramYear=2015%25");
                }
            }

            $scope.reportForward = function () {
                $scope.obj = $scope.jasperReport.id;
                var parmavar = "";
                var urlString = "";
                var reportMonth = "";
                var reportYear = "";
                //http://202.4.121.77:9090/jasperserver/flow.html?
                //// _flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2FJP_Job_Type_Wise_Report&standAlone=true
                /*urlString = "http://202.4.121.77:9090/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2F";
                urlString = urlString + $scope.obj.path + "&standAlone=true&j_username=user&j_password=password&decorate=no";*/

                urlString = "http://202.4.121.77:9090/jasperserver/flow.html?_flowId=viewReportFlow&_flowId=viewReportFlow&ParentFolderUri=%2Freports&reportUnit=%2Freports%2F";
                urlString = urlString + $scope.obj.path + "&standAlone=true&j_username=user&j_password=" + $rootScope.encCre + "&decorate=no";
                var parmavar = "";
                angular.forEach($scope.jasperReportParameters, function (data) {

                    parmavar = parmavar + "&" + data.name + "=";

                    if (data.actiontype) {
                        if (data.type == 'combo') {

                            if (data.actiontype.toString().trim() != "")
                                parmavar = parmavar + data.actiontype;
                            parmavar = parmavar + "%25";

                            if (data.actiontype.toString().trim() != ""){
                                /*console.log("report name : "+data.name);
                                console.log("report param : "+data.actiontype);*/
                                if(data.name == 'paramMonth'){
                                    reportMonth = data.actiontype.toString().trim();
                                    console.log("reportMonth : "+reportMonth);
                                } else if (data.name == 'paramYear'){
                                    reportYear = data.actiontype.toString().trim();
                                    console.log("reportYear : "+reportYear);
                                }
                            }

                        }
                        else if (data.type == 'text') {

                            if (data.actiontype.toString().trim() != "")
                                parmavar = parmavar + data.actiontype;


                            parmavar = parmavar + "%25";
                        }

                        else if (data.type == 'month' || data.type == 'year') {

                            if (data.actiontype.toString().trim() != "")
                                parmavar = parmavar + data.actiontype;
                        }
                    }
                    else {
                        parmavar = parmavar + "%25";
                    }
                });

                $scope.rptBaseurl = urlString + parmavar;
                console.log('................url');
                console.log($scope.rptBaseurl);
                $scope.url = $sce.trustAsResourceUrl($scope.rptBaseurl);


                $scope.mpoSalaryFlow.reportName = $scope.reportName;
                $scope.mpoSalaryFlow.urls = $scope.rptBaseurl;
                $scope.mpoSalaryFlow.forwardFrom = $rootScope.accountId;
                $scope.mpoSalaryFlow.forwardToRole = "ROLE_MINISTRY";
                $scope.mpoSalaryFlow.status = true;
                $scope.mpoSalaryFlow.approveStatus = "Forwarded To MINISTRY";
                $scope.mpoSalaryFlow.userLock = true;
                $scope.mpoSalaryFlow.levels = 1;
                $scope.mpoSalaryFlow.dteStatus = "Forwarded_To_MINISTRY";
                $scope.mpoSalaryFlow.dteId = $rootScope.accountId;
                $scope.mpoSalaryFlow.dteApproveDate = new Date();
                $scope.mpoSalaryFlow.reportMonth = reportMonth;
                $scope.mpoSalaryFlow.reportYear = reportYear;
                if ($scope.url != null) {

                    MpoSalaryFlow.save($scope.mpoSalaryFlow, onSaveFinished);
                }
            };

            var onSaveFinished = function (result) {
                $('#mpoSalaryFlowConfirmation').modal('show');
            };

            $scope.clear = function () {
                $('#mpoSalaryFlowConfirmation').modal('hide');

            };

        }]);
