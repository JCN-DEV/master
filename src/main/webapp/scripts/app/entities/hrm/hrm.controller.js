'use strict';

angular.module('stepApp')
    .controller('hrmHomeController', function($scope, $state, $sce, $rootScope, $timeout, DataUtils, HrEmpChildrenInfoApprover,
                                              HrEmploymentInfoApprover, HrEmpAddressInfoApprover, HrEmpProfMemberInfoApprover,
                                              HrEmpAwardInfoApprover, HrEmpForeignTourInfoApprover, HrEmpOtherServiceInfoApprover,
                                              HrEmpPublicationInfoApprover, HrEmpTrainingInfoApprover, HrEmpTransferInfoApprover,
                                              HrNomineeInfoApprover, HrSpouseInfoApprover, HrEducationInfoApprover, HrEmployeeInfoApprover,
                                              HrEmpPreGovtJobInfoApprover, HrEntertainmentBenefitApprover,
                                              HrEmpActingDutyInfoApprover, HrEmpPromotionInfoApprover, HrEmpServiceHistoryApprover,
                                              HrEmployeeInfo, Principal, HrEmployeeInfoAppRejDashboard, HrEmpBankAccountInfoApprover,
                                              HrEmpAcrInfoApprover, HrEmpTransferApplInfoApprover,
                                              HrEmpAwardInfo, HrEducationInfo, HrEmpForeignTourInfo, HrEmpPromotionInfo, HrEmpPublicationInfo,
                                              HrEmpServiceHistory, HrEmpTrainingInfo, HrEmpTransferInfo,HrEmpActingDutyInfo)
    {
        var ENTITY_CHILDREN     = "Children";
        var ENTITY_ADDRESS      = "Address";
        var ENTITY_EMPLOYMENT   = "Employement";
        var ENTITY_PROFMEMBER   = "Prof.Member";
        var ENTITY_AWARD        = "Award";
        var ENTITY_FOREIGNTOUR  = "ForeignTour";
        var ENTITY_OTHERSERVICE = "OtherService";
        var ENTITY_PUBLICATION  = "Publication";
        var ENTITY_TRAINING     = "Training";
        var ENTITY_TRANSFER     = "Transfer";
        var ENTITY_SPOUSE       = "Spouse";
        var ENTITY_NOMINEE      = "Nominee";
        var ENTITY_EDUCATION    = "Education";
        var ENTITY_EMPLOYEE     = "Employee";
        var ENTITY_PREGOVTJOB   = "PreGovtJob";
        var ENTITY_ENTERTAINMNT = "Entertainment";
        var ENTITY_SERHISTORY   = "ServiceHistory";
        var ENTITY_ACTINGDUTY   = "ActingDuty";
        var ENTITY_PROMOTION    = "Promotion";
        var ENTITY_BANKACCOUNT  = "BankAccount";
        var ENTITY_ACRMGT       = "ACR";
        var ENTITY_TRANSAPPL    = "TransferApplication";

        //$rootScope.setSuccessMessage('fdsfdfd');

        $scope.newRequestList = [];
        $scope.updateRequestList = [];
        $scope.applApprvRequestList = [];
        $scope.approvedList = [];
        $scope.rejectedList = [];
        $scope.loadingInProgress = true;
        $scope.requestEntityCounter = 0;
        $scope.totalNumberOfEntity = 22;

        $scope.loadAll = function()
        {
            $scope.requestEntityCounter = 0;
            $scope.newRequestList = [];
            $scope.updateRequestList = [];
            $scope.applApprvRequestList = [];
            $scope.selectedRequestList = [];
            $scope.selectedUpdateList = [];

            HrEmpChildrenInfoApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Children Name", "text",modelInfo.childrenName, logInfo ? logInfo.childrenName:"" ));
                    dataList.push($scope.buildDataObj("Children Name Bn", "text",modelInfo.childrenNameBn, logInfo ? logInfo.childrenNameBn:""));
                    dataList.push($scope.buildDataObj("Date Of Birth", "date",modelInfo.dateOfBirth, logInfo ? logInfo.dateOfBirth:""));
                    dataList.push($scope.buildDataObj("Gender", "text",modelInfo.gender, logInfo ? logInfo.gender:""));
                    //dataList.push($scope.buildDataObj("activeStatus", "text",modelInfo.activeStatus, logInfo ? logInfo.activeStatus:""));

                    if(modelInfo.logStatus==0)
                    {
                        if(modelInfo.logId==0)
                        {
                            var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_CHILDREN,
                                modelInfo.employeeInfo.fullName, modelInfo.childrenName +" - "+ modelInfo.gender, modelInfo.updateDate,
                                dataList, modelInfo.logStatus, modelInfo, "Children of "+modelInfo.employeeInfo.fullName, false);
                            $scope.newRequestList.push(requestObj);
                        }
                        else
                        {
                            var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_CHILDREN,
                                modelInfo.employeeInfo.fullName, modelInfo.childrenName +" - "+ modelInfo.gender, modelInfo.updateDate,
                                dataList, modelInfo.logStatus, modelInfo, "Children of "+modelInfo.employeeInfo.fullName, true);
                            $scope.updateRequestList.push(requestObj);
                        }
                    }
                });

            },function(response)
            {
                console.log("data load failed");
            });


            HrEmploymentInfoApprover.query({id:0}, function(result)
            {
                //console.log(JSON.stringify(result));
                $scope.requestEntityCounter++;
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Present Institute", "text",modelInfo.presentInstitute, logInfo ? logInfo.presentInstitute:"" ));
                    dataList.push($scope.buildDataObj("Joining Date", "date",modelInfo.joiningDate, logInfo ? logInfo.joiningDate:""));
                    dataList.push($scope.buildDataObj("Regularization Date", "date",modelInfo.regularizationDate, logInfo ? logInfo.regularizationDate:""));
                    dataList.push($scope.buildDataObj("job Conf Notice No", "text",modelInfo.jobConfNoticeNo, logInfo ? logInfo.jobConfNoticeNo:""));
                    dataList.push($scope.buildDataObj("Confirmation Date", "date",modelInfo.confirmationDate, logInfo ? logInfo.confirmationDate:""));
                    dataList.push($scope.buildDataObj("Office Order No", "text",modelInfo.officeOrderNo, logInfo ? logInfo.officeOrderNo:""));
                    dataList.push($scope.buildDataObj("Office Order Date", "date",modelInfo.officeOrderDate, logInfo ? logInfo.officeOrderDate:""));
                    dataList.push($scope.buildDataObj("Employee Type", "text",modelInfo.employeeType.typeName, logInfo ? logInfo.employeeType.typeName:""));
                    dataList.push($scope.buildDataObj("Present Payscale", "text",modelInfo.presentPayScale.payScaleCode, logInfo ? logInfo.presentPayScale.payScaleCode:""));
                    dataList.push($scope.buildDataObj("Locality Set", "text", (modelInfo.localitySetInfo != null ) ? modelInfo.localitySetInfo.name:"", (logInfo != null && logInfo.localitySetInfo !=null ) ? logInfo.localitySetInfo.name : ""));

                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_EMPLOYMENT,
                            modelInfo.employeeInfo.fullName, modelInfo.presentInstitute +" - "+ modelInfo.joiningDate, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Employment of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_EMPLOYMENT,
                            modelInfo.employeeInfo.fullName, modelInfo.presentInstitute +" - "+ modelInfo.joiningDate, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Employment of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }
                });
            });

            HrEmpAddressInfoApprover.query({id:0}, function(result)
            {
                //console.log(JSON.stringify(result));
                $scope.requestEntityCounter++;
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    var smryTxt = "";
                    dataList.push($scope.buildDataObj("Address Type", "text",modelInfo.addressType, logInfo ? logInfo.addressType:"" ));
                    dataList.push($scope.buildDataObj("Contact Number", "text",modelInfo.contactNumber, logInfo ? logInfo.contactNumber:""));
                    //dataList.push($scope.buildDataObj("Active Status", "text",modelInfo.activeStatus, logInfo ? logInfo.activeStatus:""));
                    if(modelInfo.addressType!='Emergency')
                    {
                        dataList.push($scope.buildDataObj("House Number", "text",modelInfo.houseNumber, logInfo ? logInfo.houseNumber:""));
                        dataList.push($scope.buildDataObj("House Number Bn", "text",modelInfo.houseNumberBn, logInfo ? logInfo.houseNumberBn:""));
                        dataList.push($scope.buildDataObj("Road Number", "text",modelInfo.roadNumber, logInfo ? logInfo.roadNumber:""));
                        dataList.push($scope.buildDataObj("Road Number Bn Date", "text",modelInfo.roadNumberBn, logInfo ? logInfo.roadNumberBn:""));
                        dataList.push($scope.buildDataObj("Village Name", "text",modelInfo.villageName, logInfo ? logInfo.villageName:""));
                        dataList.push($scope.buildDataObj("Village Name Bn", "text",modelInfo.villageNameBn, logInfo ? logInfo.villageNameBn:""));
                        dataList.push($scope.buildDataObj("Post Office", "text",modelInfo.postOffice, logInfo ? logInfo.postOffice:""));
                        dataList.push($scope.buildDataObj("Post Office Bn", "text",modelInfo.postOfficeBn, logInfo ? logInfo.postOfficeBn:""));
                        dataList.push($scope.buildDataObj("Division","text", (modelInfo != null && modelInfo.division != null ) ? modelInfo.division.name:"" , (logInfo != null && logInfo.division != null ) ? logInfo.division.name:""));
                        dataList.push($scope.buildDataObj("District","text", (modelInfo != null && modelInfo.district != null ) ? modelInfo.district.name:"" , (logInfo != null && logInfo.district != null ) ? logInfo.district.name:""));
                        dataList.push($scope.buildDataObj("Upazila","text", (modelInfo != null && modelInfo.upazila != null ) ? modelInfo.upazila.name:"" , (logInfo != null && logInfo.upazila != null ) ? logInfo.upazila.name:""));

                        smryTxt = modelInfo.addressType +",H:"+ modelInfo.houseNumber+",R:"+modelInfo.roadNumber;
                    }
                    if(modelInfo.addressType=='Emergency')
                    {
                        dataList.push($scope.buildDataObj("Contact Name", "text",modelInfo.contactName, logInfo ? logInfo.contactName:""));
                        dataList.push($scope.buildDataObj("Contact Name Bn", "text",modelInfo.contactNameBn, logInfo ? logInfo.contactNameBn:""));
                        dataList.push($scope.buildDataObj("Address", "text",modelInfo.address, logInfo ? logInfo.address:""));

                        smryTxt = modelInfo.addressType +",Mob:"+ modelInfo.contactNumber+",Nm:"+modelInfo.contactName;
                    }

                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_ADDRESS,
                            modelInfo.employeeInfo.fullName, smryTxt, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Address of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_ADDRESS,
                            modelInfo.employeeInfo.fullName, smryTxt, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Address of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }
                });
            });

            HrEmpProfMemberInfoApprover.query({id:0}, function(result)
            {
                //console.log(JSON.stringify(result));
                $scope.requestEntityCounter++;
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Organization Name", "text",modelInfo.organizationName, logInfo ? logInfo.organizationName:"" ));
                    dataList.push($scope.buildDataObj("Membership Number", "text",modelInfo.membershipNumber, logInfo ? logInfo.membershipNumber:""));
                    dataList.push($scope.buildDataObj("Membership Date", "date",modelInfo.membershipDate, logInfo ? logInfo.membershipDate:""));

                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_PROFMEMBER,
                            modelInfo.employeeInfo.fullName, "Org: "+modelInfo.organizationName +",Num:"+ modelInfo.membershipNumber, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Prof. Member of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_PROFMEMBER,
                            modelInfo.employeeInfo.fullName, "Org: "+modelInfo.organizationName +",Num:"+ modelInfo.membershipNumber, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Prof. Member of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }
                });
            });

            HrEmpAwardInfoApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                //console.log(JSON.stringify(result));
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Award Name", "text",modelInfo.awardName, logInfo ? logInfo.awardName:"" ));
                    dataList.push($scope.buildDataObj("Award Area", "text",modelInfo.awardArea, logInfo ? logInfo.awardArea:""));
                    dataList.push($scope.buildDataObj("Award Date", "date",modelInfo.awardDate, logInfo ? logInfo.awardDate:""));
                    dataList.push($scope.buildDataObj("Remarks", "text",modelInfo.remarks, logInfo ? logInfo.remarks:""));
                    dataList.push($scope.buildDataObj("G.O. Doc", "img","goOrderDoc", ""));
                    dataList.push($scope.buildDataObj("Cert Doc", "img2","certDocName", ""));

                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_AWARD,
                            modelInfo.employeeInfo.fullName, "Name: "+modelInfo.awardName +",Area:"+ modelInfo.awardArea, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Award Info of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_AWARD,
                            modelInfo.employeeInfo.fullName, "Name: "+modelInfo.awardName +",Area:"+ modelInfo.awardArea, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Award Info of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }
                });
            });

            HrEmpForeignTourInfoApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                //console.log(JSON.stringify(result));
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Purpose", "text",modelInfo.purpose, logInfo ? logInfo.purpose:"" ));
                    dataList.push($scope.buildDataObj("From Date", "date",modelInfo.fromDate, logInfo ? logInfo.fromDate:""));
                    dataList.push($scope.buildDataObj("To Date", "date",modelInfo.toDate, logInfo ? logInfo.toDate:""));
                    dataList.push($scope.buildDataObj("Country Name", "text",modelInfo.countryName, logInfo ? logInfo.countryName:""));
                    dataList.push($scope.buildDataObj("Office Order Number", "text",modelInfo.officeOrderNumber, logInfo ? logInfo.officeOrderNumber:""));
                    dataList.push($scope.buildDataObj("Fund Source", "text",modelInfo.fundSource, logInfo ? logInfo.fundSource:""));
                    dataList.push($scope.buildDataObj("G.O. Date", "text",modelInfo.goDate, logInfo ? logInfo.goDate:""));
                    dataList.push($scope.buildDataObj("Job Category", "text",modelInfo.jobCategory.typeName, logInfo ? logInfo.jobCategory.typeName:""));
                    dataList.push($scope.buildDataObj("G.O. Doc", "img","goOrderDoc", ""));

                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_FOREIGNTOUR,
                            modelInfo.employeeInfo.fullName, "Pur: "+modelInfo.purpose +",Country:"+ modelInfo.countryName, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Foreign Tour of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_FOREIGNTOUR,
                            modelInfo.employeeInfo.fullName, "Pur: "+modelInfo.purpose +",Country:"+ modelInfo.countryName, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Foreign Tour of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }
                });
            });

            HrEmpOtherServiceInfoApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                //console.log(JSON.stringify(result));
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Company Name", "text",modelInfo.companyName, logInfo ? logInfo.companyName:"" ));
                    dataList.push($scope.buildDataObj("Address", "text",modelInfo.address, logInfo ? logInfo.address:""));
                    dataList.push($scope.buildDataObj("Service Type", "text",modelInfo.serviceType, logInfo ? logInfo.serviceType:""));
                    dataList.push($scope.buildDataObj("Position", "text",modelInfo.position, logInfo ? logInfo.position:""));
                    dataList.push($scope.buildDataObj("From Date", "date",modelInfo.fromDate, logInfo ? logInfo.fromDate:""));
                    dataList.push($scope.buildDataObj("To Date", "date",modelInfo.toDate, logInfo ? logInfo.toDate:""));

                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_OTHERSERVICE,
                            modelInfo.employeeInfo.fullName, "Com: "+modelInfo.companyName +",Type:"+ modelInfo.serviceType, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Other Service of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_OTHERSERVICE,
                            modelInfo.employeeInfo.fullName, "Com: "+modelInfo.companyName +",Type:"+ modelInfo.serviceType, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Other Service of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }
                });
            });

            HrEmpPublicationInfoApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                //console.log(JSON.stringify(result));
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Publication Title", "text",modelInfo.publicationTitle, logInfo ? logInfo.publicationTitle:"" ));
                    dataList.push($scope.buildDataObj("Publication Date", "date",modelInfo.publicationDate, logInfo ? logInfo.publicationDate:""));
                    dataList.push($scope.buildDataObj("Remarks", "text",modelInfo.remarks, logInfo ? logInfo.remarks:""));
                    dataList.push($scope.buildDataObj("Publication Doc", "img","publicationDocName", ""));

                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_PUBLICATION,
                            modelInfo.employeeInfo.fullName, "Title: "+modelInfo.publicationTitle +",Date:"+ modelInfo.publicationDate, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Publication History of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_PUBLICATION,
                            modelInfo.employeeInfo.fullName, "Title: "+modelInfo.publicationTitle +",Date:"+ modelInfo.publicationDate, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Publication History of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }

                });
            });

            HrEmpTransferInfoApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                //console.log(JSON.stringify(result));
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Location From", "text",modelInfo.locationFrom, logInfo ? logInfo.locationFrom:"" ));
                    dataList.push($scope.buildDataObj("Location To", "text",modelInfo.locationTo, logInfo ? logInfo.locationTo:""));
                    dataList.push($scope.buildDataObj("Designation", "text",modelInfo.designation, logInfo ? logInfo.designation:""));
                    dataList.push($scope.buildDataObj("Department From", "text",modelInfo.departmentFrom, logInfo ? logInfo.departmentFrom:""));
                    dataList.push($scope.buildDataObj("Department To", "text",modelInfo.departmentTo, logInfo ? logInfo.departmentTo:""));
                    dataList.push($scope.buildDataObj("From Date", "date",modelInfo.fromDate, logInfo ? logInfo.fromDate:""));
                    dataList.push($scope.buildDataObj("To Date", "date",modelInfo.toDate, logInfo ? logInfo.toDate:""));
                    dataList.push($scope.buildDataObj("Office Order No", "text",modelInfo.officeOrderNo, logInfo ? logInfo.officeOrderNo:""));
                    dataList.push($scope.buildDataObj("G.O. Date", "date",modelInfo.goDate, logInfo ? logInfo.goDate:""));
                    dataList.push($scope.buildDataObj("Job Category", "text",modelInfo.jobCategory.typeName, logInfo ? logInfo.jobCategory.typeName:""));
                    dataList.push($scope.buildDataObj("Transfer Type", "text",modelInfo.transferType.typeName, logInfo ? logInfo.transferType.typeName:""));
                    dataList.push($scope.buildDataObj("Certificate Doc", "img","certDocName", ""));
                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_TRANSFER,
                            modelInfo.employeeInfo.fullName, "LocFrom: "+modelInfo.locationFrom +",LocTo:"+ modelInfo.locationTo, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Transfer History of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_TRANSFER,
                            modelInfo.employeeInfo.fullName, "LocFrom: "+modelInfo.locationFrom +",LocTo:"+ modelInfo.locationTo, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Transfer History of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }
                });
            });

            HrEmpTransferApplInfoApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                //console.log(JSON.stringify(result));
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Transfer Reason", "text",modelInfo.transferReason, logInfo ? logInfo.transferReason:"" ));
                    dataList.push($scope.buildDataObj("Office Order Number To", "text",modelInfo.officeOrderNumber, logInfo ? logInfo.officeOrderNumber:""));
                    dataList.push($scope.buildDataObj("Office Order Date", "date",modelInfo.officeOrderDate, logInfo ? logInfo.officeOrderDate:""));

                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_TRANSAPPL,
                            modelInfo.employeeInfo.fullName, "Ord#: "+modelInfo.officeOrderNumber +",OrdDt:"+ modelInfo.officeOrderDate, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Transfer Application of "+modelInfo.employeeInfo.fullName, false);
                        $scope.applApprvRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_TRANSAPPL,
                            modelInfo.employeeInfo.fullName, "Ord#: "+modelInfo.locationFrom +",OrdDt:"+ modelInfo.locationTo, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Transfer Application of "+modelInfo.employeeInfo.fullName, true);
                        $scope.applApprvRequestList.push(requestObj);
                    }
                });
            });

            HrNomineeInfoApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                //console.log(JSON.stringify(result));
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Nominee Name ", "text",modelInfo.nomineeName, logInfo ? logInfo.nomineeName:"" ));
                    dataList.push($scope.buildDataObj("Nominee Name Bangla", "text",modelInfo.nomineeNameBn, logInfo ? logInfo.nomineeNameBn:""));
                    dataList.push($scope.buildDataObj("Birth Date", "date",modelInfo.birthDate, logInfo ? logInfo.birthDate:""));
                    dataList.push($scope.buildDataObj("Gender", "text",modelInfo.gender, logInfo ? logInfo.gender:""));
                    dataList.push($scope.buildDataObj("Relationship", "text",modelInfo.relationship, logInfo ? logInfo.relationship:""));
                    dataList.push($scope.buildDataObj("Occupation", "text",modelInfo.occupation, logInfo ? logInfo.occupation:""));
                    dataList.push($scope.buildDataObj("Designation", "text",modelInfo.designation, logInfo ? logInfo.designation:""));
                    dataList.push($scope.buildDataObj("National Id", "text",modelInfo.nationalId, logInfo ? logInfo.nationalId:""));
                    dataList.push($scope.buildDataObj("Mobile Number", "text",modelInfo.mobileNumber, logInfo ? logInfo.mobileNumber:""));
                    dataList.push($scope.buildDataObj("Address", "text",modelInfo.address, logInfo ? logInfo.address:""));

                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_NOMINEE,
                            modelInfo.employeeInfo.fullName, "Name: "+modelInfo.nomineeName+", Rel:"+modelInfo.relationship, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Nominee Info of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_NOMINEE,
                            modelInfo.employeeInfo.fullName, "Name: "+modelInfo.nomineeName +", Rel:"+modelInfo.relationship, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Nominee Info of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }
                });
            });

            HrSpouseInfoApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                //console.log(JSON.stringify(result));
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Spouse Name", "text",modelInfo.spouseName, logInfo ? logInfo.spouseName:"" ));
                    dataList.push($scope.buildDataObj("Spouse Name Bangla", "text",modelInfo.spouseNameBn, logInfo ? logInfo.spouseNameBn:""));
                    dataList.push($scope.buildDataObj("Birth Date", "date",modelInfo.birthDate, logInfo ? logInfo.birthDate:""));
                    dataList.push($scope.buildDataObj("Gender", "text",modelInfo.gender, logInfo ? logInfo.gender:""));
                    dataList.push($scope.buildDataObj("Relationship", "text",modelInfo.relationship, logInfo ? logInfo.relationship:""));
                    dataList.push($scope.buildDataObj("Is Nominee", "text",modelInfo.isNominee, logInfo ? logInfo.isNominee:""));
                    dataList.push($scope.buildDataObj("Occupation", "text",modelInfo.occupation, logInfo ? logInfo.occupation:""));
                    dataList.push($scope.buildDataObj("Organization", "text",modelInfo.organization, logInfo ? logInfo.organization:""));
                    dataList.push($scope.buildDataObj("Designation", "text",modelInfo.designation, logInfo ? logInfo.designation:""));
                    dataList.push($scope.buildDataObj("National Id", "text",modelInfo.nationalId, logInfo ? logInfo.nationalId:""));
                    dataList.push($scope.buildDataObj("Email Address", "text",modelInfo.emailAddress, logInfo ? logInfo.emailAddress:""));
                    dataList.push($scope.buildDataObj("Contact Number", "text",modelInfo.contactNumber, logInfo ? logInfo.contactNumber:""));
                    dataList.push($scope.buildDataObj("Address", "text",modelInfo.address, logInfo ? logInfo.address:""));

                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_SPOUSE,
                            modelInfo.employeeInfo.fullName, "Name: "+modelInfo.spouseName +",Rel:"+ modelInfo.relationship, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Spouse Info of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_SPOUSE,
                            modelInfo.employeeInfo.fullName, "Name: "+modelInfo.spouseName +",Rel:"+ modelInfo.relationship, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Spouse Info of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }
                });
            });

            HrEducationInfoApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                //console.log(JSON.stringify(result));
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Exam Title", "text",modelInfo.examTitle, logInfo ? logInfo.examTitle:"" ));
                    dataList.push($scope.buildDataObj("Major Subject", "text",modelInfo.majorSubject, logInfo ? logInfo.majorSubject:""));
                    dataList.push($scope.buildDataObj("Cert. Sl Number", "text",modelInfo.certSlNumber, logInfo ? logInfo.certSlNumber:""));
                    dataList.push($scope.buildDataObj("Session Year", "text",modelInfo.sessionYear, logInfo ? logInfo.sessionYear:""));
                    dataList.push($scope.buildDataObj("Roll Number", "text",modelInfo.rollNumber, logInfo ? logInfo.rollNumber:""));
                    dataList.push($scope.buildDataObj("Institute Name", "text",modelInfo.instituteName, logInfo ? logInfo.instituteName:""));
                    dataList.push($scope.buildDataObj("GPA Or Cgpa", "text",modelInfo.gpaOrCgpa, logInfo ? logInfo.gpaOrCgpa:""));
                    dataList.push($scope.buildDataObj("GPA Scale", "text",modelInfo.gpaScale, logInfo ? logInfo.gpaScale:""));
                    dataList.push($scope.buildDataObj("Passing Year", "text",modelInfo.passingYear, logInfo ? logInfo.passingYear:""));
                    dataList.push($scope.buildDataObj("Education Level", "text",modelInfo.educationLevel.typeName, logInfo ? logInfo.educationLevel.typeName:""));
                    dataList.push($scope.buildDataObj("Education Board", "text",modelInfo.educationBoard.typeName, logInfo ? logInfo.educationBoard.typeName:""));
                    dataList.push($scope.buildDataObj("Certificate Doc", "img","certificateDocName", ""));
                    dataList.push($scope.buildDataObj("Transcript Doc", "img2","transcriptDocName", ""));

                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_EDUCATION,
                            modelInfo.employeeInfo.fullName, "Exam: "+modelInfo.examTitle +",Sub:"+ modelInfo.majorSubject, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Education Info of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_EDUCATION,
                            modelInfo.employeeInfo.fullName, "Exam: "+modelInfo.examTitle +",Sub:"+ modelInfo.majorSubject, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Eudcation Info of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }
                });
            });

            HrEmployeeInfoApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                //console.log(JSON.stringify(result));
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Full Name", "text",modelInfo.fullName, logInfo ? logInfo.fullName:"" ));
                    dataList.push($scope.buildDataObj("Employee ID", "text",modelInfo.employeeId, logInfo ? logInfo.employeeId:""));
                    dataList.push($scope.buildDataObj("Full Name Bn", "text",modelInfo.fullNameBn, logInfo ? logInfo.fullNameBn:""));
                    dataList.push($scope.buildDataObj("Father Name", "text",modelInfo.fatherName, logInfo ? logInfo.fatherName:""));
                    dataList.push($scope.buildDataObj("Father Name Bn", "text",modelInfo.fatherNameBn, logInfo ? logInfo.fatherNameBn:""));
                    dataList.push($scope.buildDataObj("Mother Name", "text",modelInfo.motherName, logInfo ? logInfo.motherName:""));
                    dataList.push($scope.buildDataObj("Mother NameBn", "text",modelInfo.motherNameBn, logInfo ? logInfo.motherNameBn:""));
                    dataList.push($scope.buildDataObj("Birth Date", "date",modelInfo.birthDate, logInfo ? logInfo.birthDate:""));
                    dataList.push($scope.buildDataObj("Date of Joining", "date",modelInfo.dateOfJoining, logInfo ? logInfo.dateOfJoining:""));
                    dataList.push($scope.buildDataObj("PRL Date", "date",modelInfo.prlDate, logInfo ? logInfo.prlDate:""));
                    dataList.push($scope.buildDataObj("Retirement Date", "date",modelInfo.retirementDate, logInfo ? logInfo.retirementDate:""));
                    dataList.push($scope.buildDataObj("Last Working Day", "date", modelInfo.lastWorkingDay, logInfo ? logInfo.lastWorkingDay : ""));
                    dataList.push($scope.buildDataObj("Apointment Go Date", "date",modelInfo.apointmentGoDate, logInfo ? logInfo.apointmentGoDate:""));
                    dataList.push($scope.buildDataObj("Present Id", "text",modelInfo.presentId, logInfo ? logInfo.presentId:""));
                    dataList.push($scope.buildDataObj("National Id", "text",modelInfo.nationalId, logInfo ? logInfo.nationalId:""));
                    dataList.push($scope.buildDataObj("Email Address", "text",modelInfo.emailAddress, logInfo ? logInfo.emailAddress:""));
                    dataList.push($scope.buildDataObj("Mobile Number", "text",modelInfo.mobileNumber, logInfo ? logInfo.mobileNumber:""));
                    dataList.push($scope.buildDataObj("Gender", "text",modelInfo.gender, logInfo ? logInfo.gender:""));
                    dataList.push($scope.buildDataObj("Birth Place", "text",modelInfo.birthPlace, logInfo ? logInfo.birthPlace:""));
                    dataList.push($scope.buildDataObj("Any Disease", "text",modelInfo.anyDisease, logInfo ? logInfo.anyDisease:""));
                    dataList.push($scope.buildDataObj("Officer Stuff", "text",modelInfo.officerStuff, logInfo ? logInfo.officerStuff:""));
                    dataList.push($scope.buildDataObj("Tin Number", "text",modelInfo.tinNumber, logInfo ? logInfo.tinNumber:""));
                    dataList.push($scope.buildDataObj("Marital Status", "text",modelInfo.maritalStatus, logInfo ? logInfo.maritalStatus:""));
                    dataList.push($scope.buildDataObj("Blood Group", "text",modelInfo.bloodGroup, logInfo ? logInfo.bloodGroup:""));
                    dataList.push($scope.buildDataObj("Nationality", "text",modelInfo.nationality, logInfo ? logInfo.nationality:""));
                    dataList.push($scope.buildDataObj("Birth Certificate No", "text",modelInfo.birthCertificateNo, logInfo ? logInfo.birthCertificateNo:""));
                    dataList.push($scope.buildDataObj("Religion", "text",modelInfo.religion, logInfo ? logInfo.religion:""));
                    dataList.push($scope.buildDataObj("Quota", "text",modelInfo.quota, logInfo ? logInfo.quota:""));
                    dataList.push($scope.buildDataObj("Employee Type", "text",modelInfo.employeeType, logInfo ? logInfo.employeeType:""));

                    dataList.push($scope.buildDataObj("Section",        "text", modelInfo.departmentInfo.departmentInfo.departmentName, (logInfo != null && logInfo.departmentInfo.departmentInfo != null ) ? logInfo.departmentInfo.departmentInfo.departmentName:""));
                    dataList.push($scope.buildDataObj("Designation",    "text", modelInfo.designationInfo.designationInfo.designationName, (logInfo != null && logInfo.designationInfo.designationInfo != null ) ? logInfo.designationInfo.designationInfo.designationName:""));
                    dataList.push($scope.buildDataObj("Org. Category",  "text", modelInfo.workArea ? modelInfo.workArea.typeName:"", (logInfo != null && logInfo.workArea != null) ? logInfo.workArea.typeName:""));
                    dataList.push($scope.buildDataObj("Org. Name",      "text", modelInfo.workAreaDtl ? modelInfo.workAreaDtl.name:"", (logInfo != null && logInfo.workAreaDtl != null) ? logInfo.workAreaDtl.name:""));
                    dataList.push($scope.buildDataObj("Employment Type","text", modelInfo.employementType ? modelInfo.employementType.typeName:"", (logInfo != null && logInfo.employementType != null) ? logInfo.employementType.typeName:""));
                    dataList.push($scope.buildDataObj("Grade Info",     "text", modelInfo.gradeInfo ? modelInfo.gradeInfo.gradeCode:"", (logInfo != null && logInfo.gradeInfo != null) ? logInfo.gradeInfo.gradeCode:""));

                    dataList.push($scope.buildDataObj("Employee Photo", "img","empPhoto", ""));
                    dataList.push($scope.buildDataObj("Quota Cert. Doc", "img2","quotaCert", ""));

                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.user.id, ENTITY_EMPLOYEE,
                            modelInfo.fullName, "Father: "+modelInfo.fatherName +",Mother:"+ modelInfo.motherName, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Employee Information : "+modelInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var haveLogData = true;
                        if(logInfo==null){ haveLogData = false;}
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.user.id, ENTITY_EMPLOYEE,
                            modelInfo.fullName, "Father: "+modelInfo.fatherName +",Mother:"+ modelInfo.motherName, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Employee Information :  "+modelInfo.fullName, haveLogData);
                        $scope.updateRequestList.push(requestObj);
                    }
                });
            });

            HrEmpPreGovtJobInfoApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                //console.log(JSON.stringify(result));
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Organization Name", "text",modelInfo.organizationName, logInfo ? logInfo.organizationName:"" ));
                    dataList.push($scope.buildDataObj("Post Name", "text",modelInfo.postName, logInfo ? logInfo.postName:""));
                    dataList.push($scope.buildDataObj("Address", "text",modelInfo.address, logInfo ? logInfo.address:""));
                    dataList.push($scope.buildDataObj("From Date", "date",modelInfo.fromDate, logInfo ? logInfo.fromDate:""));
                    dataList.push($scope.buildDataObj("To Date", "date",modelInfo.toDate, logInfo ? logInfo.toDate:""));
                    dataList.push($scope.buildDataObj("Salary", "text",modelInfo.salary, logInfo ? logInfo.salary:""));
                    dataList.push($scope.buildDataObj("Comments", "text",modelInfo.comments, logInfo ? logInfo.comments:""));

                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_PREGOVTJOB,
                            modelInfo.employeeInfo.fullName, "Org: "+modelInfo.organizationName +",Post:"+ modelInfo.postName, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Pre. Govt. Job History of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_PREGOVTJOB,
                            modelInfo.employeeInfo.fullName, "Org: "+modelInfo.organizationName +",Post:"+ modelInfo.postName, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Pre. Govt. Job History of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }

                });
            });

            HrEntertainmentBenefitApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                //console.log(JSON.stringify(result));
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Eligibility Date", "date",modelInfo.eligibilityDate, logInfo ? logInfo.eligibilityDate:"" ));
                    dataList.push($scope.buildDataObj("Amount", "text",modelInfo.amount, logInfo ? logInfo.amount:""));
                    dataList.push($scope.buildDataObj("Total Days", "text",modelInfo.totalDays, logInfo ? logInfo.totalDays:""));
                    dataList.push($scope.buildDataObj("Not Taken Reason", "text",modelInfo.notTakenReason, logInfo ? logInfo.notTakenReason:""));
                    dataList.push($scope.buildDataObj("Job Category", "text",modelInfo.jobCategory.typeName, logInfo ? logInfo.jobCategory.typeName:""));

                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_ENTERTAINMNT,
                            modelInfo.employeeInfo.fullName, "Date: "+modelInfo.eligibilityDate +",Amt:"+ modelInfo.amount+", Days:"+modelInfo.totalDays, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Entertainment Benefit of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_ENTERTAINMNT,
                            modelInfo.employeeInfo.fullName, "Date: "+modelInfo.eligibilityDate +",Amt:"+ modelInfo.amount+", Days:"+modelInfo.totalDays, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Entertainment Benefit of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }

                });
            });

            HrEmpActingDutyInfoApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                //console.log(JSON.stringify(result));
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("To Institution", "text",modelInfo.toInstitution, logInfo ? logInfo.toInstitution:"" ));
                    dataList.push($scope.buildDataObj("Designation", "text",modelInfo.designation, logInfo ? logInfo.designation:""));
                    dataList.push($scope.buildDataObj("To Department", "text",modelInfo.toDepartment, logInfo ? logInfo.toDepartment:""));
                    dataList.push($scope.buildDataObj("From Date", "date",modelInfo.fromDate, logInfo ? logInfo.fromDate:""));
                    dataList.push($scope.buildDataObj("To Date", "date",modelInfo.toDate, logInfo ? logInfo.toDate:""));
                    dataList.push($scope.buildDataObj("Office Order Number", "text",modelInfo.officeOrderNumber, logInfo ? logInfo.officeOrderNumber:""));
                    dataList.push($scope.buildDataObj("Office Order Date", "date",modelInfo.officeOrderDate, logInfo ? logInfo.officeOrderDate:""));
                    dataList.push($scope.buildDataObj("Work On Acting Duty", "text",modelInfo.workOnActingDuty, logInfo ? logInfo.workOnActingDuty:""));
                    dataList.push($scope.buildDataObj("G.O. Date", "date",modelInfo.goDate, logInfo ? logInfo.goDate:""));
                    dataList.push($scope.buildDataObj("Comments", "text",modelInfo.comments, logInfo ? logInfo.comments:""));

                    dataList.push($scope.buildDataObj("G.O. Doc", "img","goDoc", ""));

                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_ACTINGDUTY,
                            modelInfo.employeeInfo.fullName, "Inst: "+modelInfo.toInstitution +",Desig:"+ modelInfo.designation, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Acting Duty Info of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_ACTINGDUTY,
                            modelInfo.employeeInfo.fullName, "Inst: "+modelInfo.toInstitution +",Desig:"+ modelInfo.designation, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Acting Duty Info of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }
                });
            });


            HrEmpTrainingInfoApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                //console.log(JSON.stringify(result));
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Institute Name", "text",modelInfo.instituteName, logInfo ? logInfo.instituteName:"" ));
                    dataList.push($scope.buildDataObj("Course Title", "text",modelInfo.courseTitle, logInfo ? logInfo.courseTitle:""));
                    dataList.push($scope.buildDataObj("Duration From", "date",modelInfo.durationFrom, logInfo ? logInfo.durationFrom:""));
                    dataList.push($scope.buildDataObj("Duration To", "date",modelInfo.durationTo, logInfo ? logInfo.durationTo:""));
                    dataList.push($scope.buildDataObj("Result", "text",modelInfo.result, logInfo ? logInfo.result:""));
                    dataList.push($scope.buildDataObj("Office Order No", "text",modelInfo.officeOrderNo, logInfo ? logInfo.officeOrderNo:""));
                    dataList.push($scope.buildDataObj("Job Category", "text",modelInfo.jobCategory, logInfo ? logInfo.jobCategory:""));
                    dataList.push($scope.buildDataObj("Country", "text",modelInfo.country, logInfo ? logInfo.country:""));
                    dataList.push($scope.buildDataObj("Training Type", "text",modelInfo.trainingType.typeName, logInfo ? logInfo.trainingType.typeName:""));
                    dataList.push($scope.buildDataObj("G.O. Order Doc", "img","goOrderDoc", ""));
                    dataList.push($scope.buildDataObj("Certificate Doc", "img2","certDocName", ""));

                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_TRAINING,
                            modelInfo.employeeInfo.fullName, "Inst: "+modelInfo.instituteName +",Course:"+ modelInfo.courseTitle, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Training Info of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_TRAINING,
                            modelInfo.employeeInfo.fullName, "Inst: "+modelInfo.instituteName +",Course:"+ modelInfo.courseTitle, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Training Info of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }

                });
            });

            HrEmpServiceHistoryApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                //console.log(JSON.stringify(result));
                angular.forEach(result,function(dtoInfo)
                {
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Service Date", "date",modelInfo.serviceDate, logInfo ? logInfo.serviceDate:"" ));
                    dataList.push($scope.buildDataObj("Gazetted Date", "date",modelInfo.gazettedDate, logInfo ? logInfo.gazettedDate:""));
                    dataList.push($scope.buildDataObj("EncadrementDate", "date",modelInfo.encadrementDate, logInfo ? logInfo.encadrementDate:""));
                    dataList.push($scope.buildDataObj("National Seniority", "text",modelInfo.nationalSeniority, logInfo ? logInfo.nationalSeniority:""));
                    dataList.push($scope.buildDataObj("Cadre Number", "text",modelInfo.cadreNumber, logInfo ? logInfo.cadreNumber:""));
                    dataList.push($scope.buildDataObj("GO Date", "date",modelInfo.goDate, logInfo ? logInfo.goDate:""));
                    dataList.push($scope.buildDataObj("D.O. Doc", "img","goDocName", ""));

                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_SERHISTORY,
                            modelInfo.employeeInfo.fullName, "Date: "+modelInfo.serviceDate +",GazDt:"+ modelInfo.gazettedDate+", Cadre:"+modelInfo.cadreNumber, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Service History of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_SERHISTORY,
                            modelInfo.employeeInfo.fullName, "Date: "+modelInfo.serviceDate +",GazDt:"+ modelInfo.gazettedDate+", Cadre:"+modelInfo.cadreNumber, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Service History of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }
                });
            });

            HrEmpPromotionInfoApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                angular.forEach(result,function(dtoInfo)
                {
                    //console.log(JSON.stringify(dtoInfo));
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Institute From", "text",modelInfo.instituteFrom, logInfo ? logInfo.instituteFrom:"" ));
                    dataList.push($scope.buildDataObj("Institute To", "text",modelInfo.instituteTo, logInfo ? logInfo.instituteTo:""));
                    dataList.push($scope.buildDataObj("Department From", "text",modelInfo.departmentFrom, logInfo ? logInfo.departmentFrom:""));
                    dataList.push($scope.buildDataObj("Department To", "text",modelInfo.departmentTo, logInfo ? logInfo.departmentTo:""));
                    dataList.push($scope.buildDataObj("Designation From", "text",modelInfo.designationFrom, logInfo ? logInfo.designationFrom:""));
                    dataList.push($scope.buildDataObj("Designation To", "text",modelInfo.designationTo, logInfo ? logInfo.designationTo:""));
                    dataList.push($scope.buildDataObj("Joining Date", "date",modelInfo.joiningDate, logInfo ? logInfo.joiningDate:""));
                    dataList.push($scope.buildDataObj("Payscale From", "text",modelInfo.payscaleFrom, logInfo ? logInfo.payscaleFrom:""));
                    dataList.push($scope.buildDataObj("Payscale To", "text",modelInfo.payscaleTo, logInfo ? logInfo.payscaleTo:""));
                    dataList.push($scope.buildDataObj("Promotion Type", "text",modelInfo.promotionType, logInfo ? logInfo.promotionType:""));
                    dataList.push($scope.buildDataObj("Order Date", "date",modelInfo.orderDate, logInfo ? logInfo.orderDate:""));
                    dataList.push($scope.buildDataObj("Job Category", "text",modelInfo.jobCategory.typeName, logInfo ? logInfo.jobCategory.typeName:""));
                    dataList.push($scope.buildDataObj("GO Date", "date",modelInfo.goDate, logInfo ? logInfo.goDate:""));
                    dataList.push($scope.buildDataObj("Office Order No.", "text",modelInfo.officeOrderNo, logInfo ? logInfo.officeOrderNo:""));
                    dataList.push($scope.buildDataObj("G.O. Doc", "img","goOrderDoc", ""));

                    //console.log(JSON.stringify(dataList));
                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_PROMOTION,
                            modelInfo.employeeInfo.fullName, "From: "+modelInfo.instituteFrom +",To:"+ modelInfo.instituteTo+", Dept:"+modelInfo.departmentTo, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Promotion Info of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_PROMOTION,
                            modelInfo.employeeInfo.fullName, "From: "+modelInfo.instituteFrom +",To:"+ modelInfo.instituteTo+", Dept:"+modelInfo.departmentTo, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Promotion Info of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }
                });
            });

            HrEmpBankAccountInfoApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                angular.forEach(result,function(dtoInfo)
                {
                    //console.log(JSON.stringify(dtoInfo));
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("Bank Name", "text",modelInfo.bankName.typeName, logInfo ? logInfo.bankName.typeName:""));
                    dataList.push($scope.buildDataObj("Branch Name", "text",modelInfo.branchName, logInfo ? logInfo.branchName:""));
                    dataList.push($scope.buildDataObj("Account Name", "text",modelInfo.accountName, logInfo ? logInfo.accountName:"" ));
                    dataList.push($scope.buildDataObj("Account Number", "text",modelInfo.accountNumber, logInfo ? logInfo.accountNumber:""));
                    dataList.push($scope.buildDataObj("Description", "text",modelInfo.description, logInfo ? logInfo.description:""));
                    dataList.push($scope.buildDataObj("Is Salary Account", "text",modelInfo.salaryAccount, logInfo ? logInfo.salaryAccount:""));
                    //dataList.push($scope.buildDataObj("Active Status", "text",modelInfo.activeStatus, logInfo ? logInfo.activeStatus:""));

                    //console.log(JSON.stringify(dataList));
                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_BANKACCOUNT,
                            modelInfo.employeeInfo.fullName, "Bank: "+modelInfo.bankName.typeName+", Branch: "+modelInfo.branchName +",Account:"+ modelInfo.accountName, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Bank Account Info of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_BANKACCOUNT,
                            modelInfo.employeeInfo.fullName, "Bank: "+modelInfo.bankName.typeName+", Branch: "+modelInfo.branchName +",Account:"+ modelInfo.accountName, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "Bank Account Info of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }
                });
            });

            HrEmpAcrInfoApprover.query({id:0}, function(result)
            {
                $scope.requestEntityCounter++;
                angular.forEach(result,function(dtoInfo)
                {
                    //console.log(JSON.stringify(dtoInfo));
                    var modelInfo = dtoInfo.entityObject;
                    var logInfo = dtoInfo.entityLogObject;
                    var dataList = [];
                    dataList.push($scope.buildDataObj("ACR Year", "text",modelInfo.acrYear, logInfo ? logInfo.acrYear:""));
                    dataList.push($scope.buildDataObj("Total Marks", "text",modelInfo.totalMarks, logInfo ? logInfo.totalMarks:""));
                    dataList.push($scope.buildDataObj("Evaluatioin", "text",modelInfo.overallEvaluation, logInfo ? logInfo.overallEvaluation:"" ));
                    dataList.push($scope.buildDataObj("Promotion Status", "text",modelInfo.promotionStatus, logInfo ? logInfo.promotionStatus:""));
                    dataList.push($scope.buildDataObj("ACR Date", "date",modelInfo.acrDate, logInfo ? logInfo.acrDate:""));
                    //dataList.push($scope.buildDataObj("Active Status", "text",modelInfo.activeStatus, logInfo ? logInfo.activeStatus:""));

                    //console.log(JSON.stringify(dataList));
                    if(modelInfo.logId==0)
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_ACRMGT,
                            modelInfo.employeeInfo.fullName, "Year: "+modelInfo.acrYear+", Marks: "+modelInfo.totalMarks +",Eval::"+ modelInfo.overallEvaluation, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "ACR Info of "+modelInfo.employeeInfo.fullName, false);
                        $scope.newRequestList.push(requestObj);
                    }
                    else
                    {
                        var requestObj = $scope.initDashboardObject(modelInfo.id, modelInfo.employeeInfo.id, ENTITY_ACRMGT,
                            modelInfo.employeeInfo.fullName, "Year: "+modelInfo.acrYear+", Marks: "+modelInfo.totalMarks +",Eval::"+ modelInfo.overallEvaluation, modelInfo.updateDate,
                            dataList, modelInfo.logStatus, modelInfo, "ACR Info of "+modelInfo.employeeInfo.fullName, true);
                        $scope.updateRequestList.push(requestObj);
                    }
                });
            });

            $scope.loadApprovedRejectList();
        };

        $scope.initDashboardObject = function(entityId, employeeId, entityName, requestFrom, requestSummary, requestDate, dataList, logStatus, object, viewFormTitle, haveLogData)
        {
            return {
                entityId: entityId,
                employeeId:employeeId,
                entityName:entityName,
                logComments:'',
                actionType:'',
                isApproved:true,
                requestFrom:requestFrom,
                requestSummary: requestSummary,
                requestDate:requestDate,
                dataList: dataList,
                logStatus:logStatus,
                entityObject: object,
                viewFormTitle: viewFormTitle,
                haveLogData: haveLogData,
                isSelected:false,
                rowId:entityName+""+entityId
            };
        };

        $scope.processImageView = function(requestObj)
        {
            console.log("ImageView: Entity: "+requestObj.entityName+", Id: "+requestObj.entityId);
            if(requestObj.entityName == ENTITY_AWARD)
            {
                HrEmpAwardInfo.get({id : requestObj.entityId}, function(result) {
                    $scope.generateImageData(requestObj, result.goOrderDoc, result.goOrderDocContentType, 1);
                    $scope.generateImageData(requestObj, result.certDoc, result.certDocContentType, 2);
                });
            }
            else if(requestObj.entityName == ENTITY_EDUCATION)
            {
                HrEducationInfo.get({id : requestObj.entityId}, function(result) {
                    $scope.generateImageData(requestObj, result.certificateDoc, result.certificateDocContentType, 1);
                    $scope.generateImageData(requestObj, result.transcriptDoc, result.transcriptDocContentType, 2);
                });
            }
            else if(requestObj.entityName == ENTITY_FOREIGNTOUR)
            {
                HrEmpForeignTourInfo.get({id : requestObj.entityId}, function(result) {
                    $scope.generateImageData(requestObj, result.goDoc, result.goDocContentType, 1);
                });
            }
            else if(requestObj.entityName == ENTITY_PROMOTION)
            {
                HrEmpPromotionInfo.get({id : requestObj.entityId}, function(result) {
                    $scope.generateImageData(requestObj, result.goDoc, result.goDocContentType, 1);
                });
            }
            else if(requestObj.entityName == ENTITY_PUBLICATION)
            {
                HrEmpPublicationInfo.get({id : requestObj.entityId}, function(result) {
                    $scope.generateImageData(requestObj, result.publicationDoc, result.publicationDocContentType, 1);
                });
            }
            else if(requestObj.entityName == ENTITY_SERHISTORY)
            {
                HrEmpServiceHistory.get({id : requestObj.entityId}, function(result) {
                    $scope.generateImageData(requestObj, result.goDoc, result.goDocContentType, 1);
                });
            }
            else if(requestObj.entityName == ENTITY_TRAINING)
            {
                HrEmpTrainingInfo.get({id : requestObj.entityId}, function(result)
                {
                    $scope.generateImageData(requestObj, result.goOrderDoc, result.goOrderDocContentType, 1);
                    $scope.generateImageData(requestObj, result.certDoc, result.certDocContentType, 2);
                });
            }
            else if(requestObj.entityName == ENTITY_TRANSFER)
            {
                HrEmpTransferInfo.get({id : requestObj.entityId}, function(result) {
                    $scope.generateImageData(requestObj, result.goDoc, result.goDocContentType, 1);
                });
            }
            else if(requestObj.entityName == ENTITY_EMPLOYEE)
            {
                HrEmployeeInfo.get({id : requestObj.entityId}, function(result) {
                    $scope.generateImageData(requestObj, result.empPhoto, result.empPhotoContentType, 1);
                    $scope.generateImageData(requestObj, result.quotaCert, result.quotaCertContentType, 2);
                });
            }
            else if(requestObj.entityName == ENTITY_ACTINGDUTY)
            {
                HrEmpActingDutyInfo.get({id : requestObj.entityId}, function(result) {
                    $scope.generateImageData(requestObj, result.goDoc, result.goDocContentType, 1);
                });
            }

        };

        $scope.generateImageData = function (requestObj, imgData, contentType, imgOption)
        {
            if (imgData) {
                var blob = $rootScope.b64toBlob(imgData, contentType);
                requestObj.filePath = (window.URL || window.webkitURL).createObjectURL(blob);
                if(imgOption==1)
                    requestObj.fileContentUrl = $sce.trustAsResourceUrl(requestObj.filePath);
                else if (imgOption==2)
                    requestObj.fileContentUrl2 = $sce.trustAsResourceUrl(requestObj.filePath);
            }
        };

        $scope.approvalViewAction = function ()
        {
            if($scope.approvalObj.isApproved)
                $scope.approvalObj.actionType='accept';
            else $scope.approvalObj.actionType='reject';
            $scope.approvalAction($scope.approvalObj);
        };

        $scope.approvalAction = function (requestObj)
        {
            console.log("Request: "+requestObj.rowId+", action: "+requestObj.actionType+", haveLog: "+requestObj.haveLogData);
            //return;

            if(requestObj.entityName==ENTITY_ADDRESS)
            {
                HrEmpAddressInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.addressInfoUpdate');
                    //$scope.clear();
                });

            }
            else if(requestObj.entityName == ENTITY_CHILDREN)
            {
                HrEmpChildrenInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    //$scope.clear();
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.childrenInfoUpdate');
                });
            }
            else if(requestObj.entityName == ENTITY_EMPLOYMENT)
            {
                HrEmploymentInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.employmentInfoUpdate');
                    //$scope.clear();
                });
            }
            else if(requestObj.entityName == ENTITY_PROFMEMBER)
            {
                HrEmpProfMemberInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.profMembershipInfoUpdate');
                    //$scope.clear();
                });
            }
            else if(requestObj.entityName == ENTITY_AWARD)
            {
                HrEmpAwardInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.awardInfoUpdate');
                    //$scope.clear();
                });
            }
            else if(requestObj.entityName == ENTITY_FOREIGNTOUR)
            {
                HrEmpForeignTourInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.foreignTourInfoUpdate');
                    //$scope.clear();
                });
            }
            else if(requestObj.entityName == ENTITY_OTHERSERVICE)
            {
                HrEmpOtherServiceInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.otherServiceInfoUpdate');
                    //$scope.clear();
                });
            }
            else if(requestObj.entityName == ENTITY_PUBLICATION)
            {
                HrEmpPublicationInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.publicationInfoUpdate');
                    //$scope.clear();
                });
            }
            else if(requestObj.entityName == ENTITY_TRAINING)
            {
                HrEmpTrainingInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.trainingInfoUpdate');
                    //$scope.clear();
                });
            }
            else if(requestObj.entityName == ENTITY_TRANSFER)
            {
                HrEmpTransferInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.transferInfoUpdate');
                    //$scope.clear();
                });
            }
            else if(requestObj.entityName == ENTITY_TRANSAPPL)
            {
                HrEmpTransferApplInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.transferApplicationInfoUpdate');
                    //$scope.clear();
                });
            }
            else if(requestObj.entityName == ENTITY_NOMINEE)
            {
                HrNomineeInfoApprover.update(requestObj, function(result)
                {
                    //$scope.loadAll();
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.nomineeInfoUpdate');
                    //$scope.clear();
                });
            }
            else if(requestObj.entityName == ENTITY_SPOUSE)
            {
                HrSpouseInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.spouseInfoUpdate');
                    //$scope.clear();
                });
            }
            else if(requestObj.entityName == ENTITY_EDUCATION)
            {
                HrEducationInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.eductionInfoUpdate');
                    //$scope.clear();
                });
            }
            else if(requestObj.entityName == ENTITY_EMPLOYEE)
            {
                HrEmployeeInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.employeeInfoUpdate');
                    //$scope.clear();
                });
            }
            else if(requestObj.entityName == ENTITY_PREGOVTJOB)
            {
                HrEmpPreGovtJobInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.preGovtInfoUpdate');
                    //$scope.clear();
                });
            }
            else if(requestObj.entityName == ENTITY_ENTERTAINMNT)
            {
                HrEntertainmentBenefitApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.entertainmentBenefitInfoUpdate');
                    //$scope.clear();
                });
            }

            else if(requestObj.entityName == ENTITY_ACTINGDUTY)
            {
                HrEmpActingDutyInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.actingDutyInfoUpdate');
                    //$scope.clear();
                });
            }

            else if(requestObj.entityName == ENTITY_PROMOTION)
            {
                HrEmpPromotionInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.promotionInfoUpdate');
                    //$scope.clear();
                });
            }

            else if(requestObj.entityName == ENTITY_SERHISTORY)
            {
                HrEmpServiceHistoryApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.serviceHistoryInfoUpdate');
                    //$scope.clear();
                });
            }

            else if(requestObj.entityName == ENTITY_BANKACCOUNT)
            {
                HrEmpBankAccountInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.bankAccountInfoUpdate');
                });
            }

            else if(requestObj.entityName == ENTITY_ACRMGT)
            {
                HrEmpAcrInfoApprover.update(requestObj, function(result)
                {
                    $scope.removeDataFromList(requestObj);
                    $('#approveRejectConfirmation').modal('hide');
                    $('#approveViewDetailForm').modal('hide');
                    $rootScope.setSuccessMessage('stepApp.hrmHome.messages.acrInfoUpdate');
                });
            }
        };

        $scope.loadApprovedRejectList = function ()
        {
            $scope.approvedList = [];
            $scope.rejectedList = [];
            HrEmployeeInfoAppRejDashboard.query({}, function(result)
            {
                angular.forEach(result,function(requestObj)
                {
                    if(requestObj.logStatus==2)
                    {
                        $scope.approvedList.push(requestObj);
                    }
                    else
                    {
                        $scope.rejectedList.push(requestObj);
                    }
                });
            },function(response)
            {
                console.log("data from view load failed");
            });
        };

        $scope.selectedRequestList = [];
        $scope.selectedUpdateList = [];


        $scope.searchText = "";
        $scope.updateSearchText = "";

        $scope.clearSearchText = function (source)
        {
            console.log("Pre: 1: "+$scope.searchText+", 2: "+$scope.updateSearchText);
            if(source=='request')
            {
                $timeout(function(){
                     $('#searchText').val('');
                     angular.element('#searchText').triggerHandler('change');
                });
            }
            if(source=='update')
            {
                $timeout(function(){
                     $('#updateSearchText').val('');
                     angular.element('#updateSearchText').triggerHandler('change');
                });
            }
            if(source=='applAppr')
            {
                /*$timeout(function(){
                    $('#updateSearchText').val('');
                    angular.element('#updateSearchText').triggerHandler('change');
                });*/
            }

            console.log("Post: 1: "+$scope.searchText+", 2: "+$scope.updateSearchText);
        };

        $scope.addRemoveRequestList = function(requestObj)
        {
            console.log("DataList: "+$scope.selectedRequestList.length+", emp: "+requestObj.requestFrom+", src: "+ $scope.searchText);
            if(requestObj.isSelected)
            {
                $scope.searchText = requestObj.requestFrom;
                console.log("Add -> " + requestObj.entityName);
                $scope.selectedRequestList.push(requestObj);
                $timeout(function(){
                     $('#searchText').val($scope.searchText);
                });
            }
            else
            {
                var indx = $scope.selectedRequestList.indexOf(requestObj);
                console.log("Removing -> "+requestObj.entityName+", indx: "+indx);
                $scope.selectedRequestList.splice(indx, 1);

                if($scope.selectedRequestList.length<1) {
                    $timeout(function(){
                        $('#searchText').val('');
                        angular.element('#searchText').triggerHandler('change');
                    });
                }
            }

        };

        $scope.addRemoveUpdateList = function(requestObj)
        {
            if(requestObj.isSelected)
            {
                $scope.updateSearchText = requestObj.requestFrom;
                console.log("Add -> " + requestObj.entityName);
                $scope.selectedUpdateList.push(requestObj);
                $timeout(function(){
                     $('#updateSearchText').val($scope.updateSearchText);
                });
            }
            else
            {
                var indx = $scope.selectedUpdateList.indexOf(requestObj);
                console.log("Removing -> "+requestObj.entityName+", indx: "+indx);
                $scope.selectedUpdateList.splice(indx, 1);

                if($scope.selectedUpdateList.length<1) {
                    $timeout(function(){
                        $('#updateSearchText').val('');
                        angular.element('#updateSearchText').triggerHandler('change');
                    });
                }
            }
        };

        $scope.buildDataObj = function(levelTitle, dataType, data1Value, data2Value)
        {
            return {
                dataLevel: levelTitle,
                dataType: dataType,
                data1Value: data1Value,
                data2Value: data2Value
            };
        };

        $scope.removeDataFromList = function(requestObj)
        {
            if(requestObj.haveLogData==true)
            {
                var indx = $scope.updateRequestList.indexOf(requestObj);
                console.log("RemovingOld -> "+requestObj.entityName+", indx: "+indx);
                $scope.updateRequestList.splice(indx, 1);

                var indx2 = $scope.selectedUpdateList.indexOf(requestObj);
                $scope.selectedUpdateList.splice(indx2, 1);
            }
            else
            {
                var indx = $scope.newRequestList.indexOf(requestObj);
                console.log("RemovingNew -> "+requestObj.entityName+", indx: "+indx);
                $scope.newRequestList.splice(indx, 1);

                var indx2 = $scope.selectedRequestList.indexOf(requestObj);
                $scope.selectedRequestList.splice(indx2, 1);
            }
            //$scope.loadAll();
        };

        $scope.approvalViewDetail = function (dataObj)
        {
            $scope.approvalObj = dataObj;
            $('#approveViewDetailForm').modal('show');
        };

        $scope.approvalConfirmation = function (dataObj, actionType)
        {
            $scope.approvalObj = dataObj;
            $scope.approvalObj.actionType = actionType;
            $('#approveRejectConfirmation').modal('show');
        };

        $scope.approvalAllConfirmation = function (actionType, source)
        {
            if(source=='request')
            {
                //$scope.approvalObj.actionType = actionType;
                //$('#approveRejectConfirmation').modal('show');
                angular.forEach($scope.selectedRequestList,function(requestObj)
                {
                    requestObj.actionType = actionType;
                    console.log("rowid-new: "+requestObj.rowId+", action:"+requestObj.actionType);

                });
            }
            if(source=='update')
            {
                angular.forEach($scope.selectedUpdateList,function(requestObj)
                {
                    requestObj.actionType = actionType;
                    console.log("rowid-update: "+requestObj.rowId+", action:"+requestObj.actionType);

                });
            }
            if(source=='applAppr')
            {
                angular.forEach($scope.selectedUpdateList,function(requestObj)
                {
                    //requestObj.actionType = actionType;
                    console.log("rowid-applAppr: "+requestObj.rowId+", action:"+requestObj.actionType);

                });
            }
        };

        $scope.clear = function () {
            $scope.approvalObj = {
                entityId: null,
                employeeId:null,
                entityName:null,
                requestFrom:null,
                requestSummary: null,
                requestDate:null,
                approveState: null,
                logStatus:null,
                logComments:null,
                actionType:'',
                entityObject: null
            };
        };

        $rootScope.$on('onEntityApprovalProcessCompleted', function(event, data)
        {
            console.log("EmitAndOn-EntityApproval"+JSON.stringify(data));
            $scope.loadAll();
        });


        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.userRole = 'ROLE_HRM_USER';

        Principal.identity().then(function (account) {
            $scope.account = account;

            if($scope.isInArray('ROLE_HRM_ADMIN', $scope.account.authorities))
            {
                $scope.userRole = 'ROLE_HRM_ADMIN';
                $scope.loadAll();
            }
            else if($scope.isInArray('ROLE_EMPLOYER', $scope.account.authorities))
            {
                $scope.userRole = 'ROLE_EMPLOYER';
            }
            else if($scope.isInArray('ROLE_HRM_USER', $scope.account.authorities))
            {
                $scope.userRole = 'ROLE_HRM_USER';
                $scope.loadEmployee();
            }
        });

        $scope.isInArray = function isInArray(value, array) {
            return array.indexOf(value) > -1;
        };

        $scope.loadEmployee = function ()
        {
            console.log("loadEmployeeProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrEmployeeInfo.get({id: 'my'}, function (result) {
                $scope.hrEmployeeInfo = result;

                if ($scope.hrEmployeeInfo.empPhoto) {
                    var blob = $rootScope.b64toBlob($scope.hrEmployeeInfo.empPhoto, $scope.hrEmployeeInfo.empPhotoContentType);
                    $scope.filePath = (window.URL || window.webkitURL).createObjectURL(blob);
                    $scope.fileContentUrl = $sce.trustAsResourceUrl($scope.filePath);
                }

                //$scope.updatePrlDate();

            }, function (response) {
                console.log("error: "+response);
                $scope.hasProfile = false;
                $scope.noEmployeeFound = true;
                $scope.isSaving = false;
            })
        };

        $scope.updatePrlDate = function()
        {
            console.log("birthdate: "+$scope.hrEmployeeInfo.birthDate+", nid:"+$scope.hrEmployeeInfo.quota);
            var prlYear = 61;
            if($scope.hrEmployeeInfo.quota=='Others' || $scope.hrEmployeeInfo.quota == 'Other' || $scope.hrEmployeeInfo.quota == 'others' || $scope.hrEmployeeInfo.quota == 'other')
            {
                prlYear = 59;
            }
            var brDt = new Date($scope.hrEmployeeInfo.birthDate);

            console.log("BirthDate: "+brDt+", PrdYear: "+prlYear);
            var prlDt = new Date(brDt.getFullYear() + prlYear, brDt.getMonth(), brDt.getDate());
            $scope.hrEmployeeInfo.prlDate = prlDt;
            console.log("PrlDate: "+$scope.hrEmployeeInfo.prlDate+", BirthDate: "+$scope.hrEmployeeInfo.birthDate);
        };

        $scope.sort = function(keyname, source)
        {
            if(source=='request')
            {
                //console.log("sortkey: "+keyname+", reverse: "+$scope.reverse);
                $scope.sortKey = keyname;   //set the sortKey to the param passed
                $scope.reverse = !$scope.reverse; //if true make it false and vice versa
            }
            else if(source=='update')
            {
                $scope.sortKey2 = keyname;   //set the sortKey to the param passed
                $scope.reverse2 = !$scope.reverse2; //if true make it false and vice versa
            }
            else if(source=='applAppr')
            {
                $scope.sortKey3 = keyname;
                $scope.reverse3 = !$scope.reverse3;
            }
            else if(source=='approved')
            {
                $scope.sortKey4 = keyname;
                $scope.reverse4 = !$scope.reverse4;
            }
            else if(source=='rejected')
            {
                $scope.sortKey5 = keyname;
                $scope.reverse5 = !$scope.reverse5;
            }

        };

    })
    .controller('hrmWelcomeHomeController',
    ['$scope', '$state', 'DataUtils',
    function($scope, $state, DataUtils )
    {
        $scope.loadAll = function() {
            ContentUpload.query({page: $scope.page, size:5}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.contentUploads = result;
            });
        };
    }]);

