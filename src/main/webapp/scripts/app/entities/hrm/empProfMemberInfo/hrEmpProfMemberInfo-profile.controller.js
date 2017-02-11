'use strict';

angular.module('stepApp').controller('HrEmpProfMemberInfoProfileController',
    ['$scope', '$stateParams', '$state', 'HrEmpProfMemberInfo', 'HrEmployeeInfo','User','Principal','DateUtils','MiscTypeSetupByCategory',
        function($scope, $stateParams, $state, HrEmpProfMemberInfo, HrEmployeeInfo, User, Principal, DateUtils,MiscTypeSetupByCategory) {

        $scope.hrEmpProfMemberInfo = {};
        $scope.hremployeeinfos  = HrEmployeeInfo.query();
        $scope.memberCatList    = MiscTypeSetupByCategory.get({cat:'MembershipCategory',stat:'true'});

        $scope.load = function(id) {
            HrEmpProfMemberInfo.get({id : id}, function(result) {
                $scope.hrEmpProfMemberInfo = result;
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
            console.log("loadPreGovtJobProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrEmpProfMemberInfo.query({id: 'my'}, function (result)
            {
                console.log("result:, len: "+result.length);
                $scope.hrEmpProfMemberInfoList = result;
                if($scope.hrEmpProfMemberInfoList.length < 1)
                {
                    $scope.addMode = true;
                    $scope.hrEmpProfMemberInfoList.push($scope.initiateModel());
                    $scope.loadEmployee();
                }
                else
                {
                    $scope.hrEmployeeInfo = $scope.hrEmpProfMemberInfoList[0].employeeInfo;
                    angular.forEach($scope.hrEmpProfMemberInfoList,function(modelInfo)
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
                    if($scope.hrEmpProfMemberInfoList.length > 1)
                    {
                        var indx = $scope.hrEmpProfMemberInfoList.indexOf(modelInfo);
                        $scope.hrEmpProfMemberInfoList.splice(indx, 1);
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
            $scope.hrEmpProfMemberInfoList.push(
                {
                    viewMode:false,
                    viewModeText:'Cancel',
                    organizationName: null,
                    membershipNumber: null,
                    membershipDate: null,
                    logId: null,
                    logStatus: null,
                    activeStatus: true,
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
                organizationName: null,
                membershipNumber: null,
                membershipDate: null,
                logId: null,
                logStatus: null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };

        $scope.calendar = {
            opened: {},
            dateFormat: 'dd-MM-yyyy',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrEmpProfMemberInfoUpdate', result);
            $scope.isSaving = false;
            $scope.hrEmpProfMemberInfoList[$scope.selectedIndex].id=result.id;
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
                modelInfo.logStatus = 2;
                HrEmpProfMemberInfo.update(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                modelInfo.isLocked = true;
            }
            else
            {
                modelInfo.logId = 0;
                modelInfo.logStatus = 1;
                modelInfo.employeeInfo = $scope.hrEmployeeInfo;
                modelInfo.createBy = $scope.loggedInUser.id;
                modelInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());

                HrEmpProfMemberInfo.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                $scope.addMode = false;
                modelInfo.isLocked = true;
            }
        };

        $scope.clear = function() {

        };
}]);
