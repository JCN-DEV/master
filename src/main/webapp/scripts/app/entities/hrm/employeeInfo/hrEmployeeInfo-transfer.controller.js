'use strict';

angular.module('stepApp').controller('HrEmployeeInfoTransferController',
    ['$rootScope','$sce','$scope', '$stateParams', '$state', '$q', 'DataUtils', 'HrEmployeeInfo', 'hrEmployeeInfosByDesigType', 'HrDesignationSetupByType', 'User','Principal','DateUtils','MiscTypeSetupByCategory','HrEmpWorkAreaDtlInfoByStatus','HrEmployeeInfoDesigLimitCheck','HrEmployeeInfoTransfer',
        function($rootScope, $sce, $scope, $stateParams, $state, $q, DataUtils, HrEmployeeInfo, hrEmployeeInfosByDesigType, HrDesignationSetupByType, User, Principal,DateUtils,MiscTypeSetupByCategory,HrEmpWorkAreaDtlInfoByStatus,HrEmployeeInfoDesigLimitCheck,HrEmployeeInfoTransfer) {

        $scope.hrEmployeeInfo = {};

        $scope.loggedInUser =   {};

        $scope.hrdesignationsetups  = HrDesignationSetupByType.get({type:'HRM'});
        $scope.hrDesigSetupListFltr = $scope.hrdesignationsetups;
        $scope.employeeInfoList     = hrEmployeeInfosByDesigType.get({desigType:'Teacher'});

        $scope.workAreaList         = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});
        $scope.workAreaDtlList      = HrEmpWorkAreaDtlInfoByStatus.get({stat:'true'});
        $scope.workAreaDtlListFiltr = $scope.workAreaDtlList;

        $scope.loadWorkAreaDetailByWork = function(workArea)
        {
            $scope.workAreaDtlListFiltr = [];
            console.log("WorkAreaOrOrg:"+JSON.stringify(workArea)+"");
            angular.forEach($scope.workAreaDtlList,function(workAreaDtl)
            {
                if(workArea.id == workAreaDtl.workArea.id){
                    $scope.workAreaDtlListFiltr.push(workAreaDtl);
                }
            });
        };

        $scope.loadDesigDeptByOrganization = function (orgnization)
        {
            $scope.hrDesigSetupListFltr = [];
            console.log("Total Desig: "+$scope.hrdesignationsetups.length);
            angular.forEach($scope.hrdesignationsetups,function(desigInfo)
            {
                if(desigInfo.organizationType=='Organization')
                {
                    if(desigInfo.organizationInfo != null)
                    {
                        //console.log("orgId: "+desigInfo.organizationInfo.id + ", Srcorgid; "+orgnization.id);
                        if(orgnization.id === desigInfo.organizationInfo.id)
                        {
                            $scope.hrDesigSetupListFltr.push(desigInfo);
                        }
                    }
                }
            });
        };

        $scope.noOfTotalEmployeeInDesig = 0;
        $scope.noOfEmployeeInDesig = 0;
        $scope.employeeDesigLimitAllowed = true;
        $scope.checkDesignationLimit = function(desigInfo)
        {
            var refId = 0;
            if($scope.hrEmployeeInfo.organizationType=='Organization')
            {
                refId = $scope.hrEmployeeInfo.workAreaDtl.id;
            }
            else{
                refId = $scope.hrEmployeeInfo.institute.id;
            }
            if(desigInfo != null)
            {
                console.log("Type: "+$scope.hrEmployeeInfo.organizationType+", desigId: "+desigInfo.id+", refId: "+refId);
                HrEmployeeInfoDesigLimitCheck.get({orgtype:$scope.hrEmployeeInfo.organizationType, desigId: desigInfo.id, refid:refId}, function (result)
                {
                    console.log("DesigResult: "+JSON.stringify(result));
                    $scope.employeeDesigLimitAllowed = result.isValid;
                    $scope.noOfTotalEmployeeInDesig = result.totalEmployee;
                    $scope.noOfEmployeeInDesig = desigInfo.elocattedPosition;
                    $scope.isSaving = !result.isValid;
                    //console.log("Total: "+result.totalEmployee+", DesigVal: "+desigInfo.elocattedPosition);
                },function(response)
                {
                    console.log("data connection failed");
                });

                $scope.hrEmployeeInfo.gradeInfo = desigInfo.gradeInfo;
            }
        };

        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                });
            });
        };

        $scope.getLoggedInUser();

        $scope.viewMode = true;
        $scope.addMode = false;
        $scope.noEmployeeFound = false;

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrEmployeeInfoUpdate', result);
            $scope.isSaving = false;
            $scope.responseMsg = result;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
            $scope.responseMsg = result;
        };

        $scope.transferEmployee = function (modelInfo)
        {
            console.log("transfer employee to DTE");
            $scope.isSaving = true;
            modelInfo.organizationType  = 'Organization';
            modelInfo.employeeType      = 'DTE_Employee';
            modelInfo.activeStatus = true;
            console.log(JSON.stringify(modelInfo));

            if (modelInfo.id != null)
            {
                modelInfo.logStatus = 1;
                HrEmployeeInfoTransfer.update(modelInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
        };

}]);
