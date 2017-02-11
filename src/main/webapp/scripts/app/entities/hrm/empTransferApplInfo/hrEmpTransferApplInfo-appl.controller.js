'use strict';

angular.module('stepApp').controller('HrEmpTransferApplInfoApplicationController',
    ['$scope', '$state', '$stateParams', 'entity', 'HrEmpTransferApplInfo', 'HrEmployeeInfo', 'MiscTypeSetupByCategory', 'HrEmpWorkAreaDtlInfo', 'HrDesignationSetup','HrEmployeeInfoByWorkArea','Principal','User','DateUtils',
        function($scope, $state, $stateParams, entity, HrEmpTransferApplInfo, HrEmployeeInfo, MiscTypeSetupByCategory, HrEmpWorkAreaDtlInfo, HrDesignationSetup,HrEmployeeInfoByWorkArea, Principal,User,DateUtils) {

        $scope.hrEmpTransferApplInfo = {};
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.hrempworkareadtlinfos = HrEmpWorkAreaDtlInfo.query();
        $scope.orgNameFilterListOne = $scope.hrempworkareadtlinfos;
        $scope.orgNameFilterListTwo = $scope.hrempworkareadtlinfos;
        $scope.orgNameFilterListThree = $scope.hrempworkareadtlinfos;
        $scope.hrdesignationsetups = HrDesignationSetup.query();
        $scope.load = function(id) {
            HrEmpTransferApplInfo.get({id : id}, function(result) {
                $scope.hrEmpTransferApplInfo = result;
            });
        };

        $scope.hremployeeinfos  = [];
        $scope.workAreaList  = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});

        $scope.loadModelByWorkArea = function(workArea)
        {
            HrEmployeeInfoByWorkArea.get({areaid : workArea.id}, function(result) {
                $scope.hremployeeinfos = result;
            });
        };

        $scope.loadOrganizationNameByCategoryOne = function(organizationCategory)
        {
            $scope.orgNameFilterListOne = [];
            angular.forEach($scope.hrempworkareadtlinfos,function(orgNameObj)
            {
                if(organizationCategory.id == orgNameObj.workArea.id){
                    $scope.orgNameFilterListOne.push(orgNameObj);
                }
            });
        };

        $scope.loadOrganizationNameByCategoryTwo = function(organizationCategory)
        {
            $scope.orgNameFilterListTwo = [];
            angular.forEach($scope.hrempworkareadtlinfos,function(orgNameObj)
            {
                if(organizationCategory.id == orgNameObj.workArea.id){
                    $scope.orgNameFilterListTwo.push(orgNameObj);
                }
            });
        };

        $scope.loadOrganizationNameByCategoryThree = function(organizationCategory)
        {
            $scope.orgNameFilterListThree = [];
            angular.forEach($scope.hrempworkareadtlinfos,function(orgNameObj)
            {
                if(organizationCategory.id == orgNameObj.workArea.id){
                    $scope.orgNameFilterListThree.push(orgNameObj);
                }
            });
        };

        $scope.loggedInUser =   {};
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

        $scope.loadModel = function()
        {
            console.log("loadTransferApplicationProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrEmpTransferApplInfo.query({id: 'my'}, function (result)
            {
                console.log("result:, len: "+result.length);
                $scope.hrEmpTransferApplInfoList = result;
                if($scope.hrEmpTransferApplInfoList.length < 1)
                {
                    $scope.addMode = true;
                    $scope.hrEmpTransferApplInfoList.push($scope.initiateModel());
                    $scope.loadEmployee();
                }
                else
                {
                    $scope.hrEmployeeInfo = $scope.hrEmpTransferApplInfoList[0].employeeInfo;
                    angular.forEach($scope.hrEmpTransferApplInfoList,function(modelInfo)
                    {
                        modelInfo.viewMode = true;
                        modelInfo.viewModeText = "Edit";
                        modelInfo.isLocked = false;
                        if(modelInfo.logStatus==0)
                        {
                            modelInfo.isLocked = true;
                        }
                    });
                }
            }, function (response)
            {
                console.log("error: "+response);
                $scope.hasProfile = false;
                $scope.addMode = true;
                $scope.loadEmployee();
            })
        };

        $scope.loadEmployee = function ()
        {
            console.log("loadEmployeeProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrEmployeeInfo.get({id: 'my'}, function (result) {
                $scope.hrEmployeeInfo = result;

            }, function (response) {
                console.log("error: "+response);
                $scope.hasProfile = false;
                $scope.noEmployeeFound = true;
                $scope.isSaving = false;
            })
        };
        $scope.loadModel();

        $scope.changeProfileMode = function (modelInfo)
        {
            if(modelInfo.viewMode)
            {
                modelInfo.viewMode = false;
                modelInfo.viewModeText = "Cancel";
            }
            else
            {
                modelInfo.viewMode = true;
                if(modelInfo.id==null)
                {
                    if($scope.hrEmpTransferApplInfoList.length > 1)
                    {
                        var indx = $scope.hrEmpTransferApplInfoList.indexOf(modelInfo);
                        $scope.hrEmpTransferApplInfoList.splice(indx, 1);
                    }
                    else
                    {
                        modelInfo.viewModeText = "Add";
                    }
                }
                else
                    modelInfo.viewModeText = "Edit";
            }
        };

        $scope.addMore = function()
        {
            $scope.hrEmpTransferApplInfoList.push(
                {
                    viewMode:false,
                    viewModeText:'Cancel',
                    transferReason: null,
                    officeOrderNumber: null,
                    officeOrderDate: null,
                    activeStatus: true,
                    selectedOption: 0,
                    logId: null,
                    logStatus: null,
                    logComments: null,
                    createDate: null,
                    createBy: null,
                    updateDate: null,
                    updateBy: null,
                    id: null
                }
            );
        };

        $scope.initiateModel = function()
        {
            return {
                viewMode:true,
                viewModeText:'Add',
                transferReason: null,
                officeOrderNumber: null,
                officeOrderDate: null,
                activeStatus: true,
                selectedOption: 0,
                logId: null,
                logStatus: null,
                logComments: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };


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

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrEmpTransferApplInfoUpdate', result);
            $scope.isSaving = false;
            $scope.hrEmpTransferApplInfoList[$scope.selectedIndex].id=result.id;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };


        $scope.updateProfile = function (modelInfo, index)
        {
            $scope.selectedIndex = index;
            modelInfo.updateBy = $scope.loggedInUser.id;
            modelInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            modelInfo.activeStatus = true;

            if (modelInfo.id != null)
            {
                modelInfo.logId = 0;
                modelInfo.logStatus = 0;
                HrEmpTransferApplInfo.update(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                modelInfo.isLocked = true;
            }
            else
            {
                modelInfo.logId = 0;
                modelInfo.logStatus = 0;
                modelInfo.employeeInfo = $scope.hrEmployeeInfo;
                modelInfo.createBy = $scope.loggedInUser.id;
                modelInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                modelInfo.selectedOption = 0;
                HrEmpTransferApplInfo.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                $scope.addMode = false;
                modelInfo.isLocked = true;
            }
        };

        $scope.clear = function() {

        };
}]);
