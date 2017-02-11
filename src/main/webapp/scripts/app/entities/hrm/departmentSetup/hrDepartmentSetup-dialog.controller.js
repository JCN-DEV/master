'use strict';

angular.module('stepApp').controller('HrDepartmentSetupDialogController',
    ['$scope','$rootScope', '$stateParams', '$state', 'entity', 'HrDepartmentSetup','Principal','User','DateUtils','MiscTypeSetupByCategory','HrEmpWorkAreaDtlInfoByStatus','HrDepartmentHeadSetupByStatus','HrDepartmentSetupUniqueness','InstCategory','InstituteByCategory','Institute','HrDepartmentHeadInfoByDept','HrEmployeeInfosByDesigLevel','HrWingSetupByStatus',
        function($scope, $rootScope, $stateParams, $state, entity, HrDepartmentSetup, Principal, User, DateUtils, MiscTypeSetupByCategory,HrEmpWorkAreaDtlInfoByStatus,HrDepartmentHeadSetupByStatus,HrDepartmentSetupUniqueness,InstCategory,InstituteByCategory,Institute,HrDepartmentHeadInfoByDept,HrEmployeeInfosByDesigLevel,HrWingSetupByStatus) {

        $scope.hrDepartmentSetup = entity;
        $scope.hrDepartmentHeadList = HrDepartmentHeadSetupByStatus.get({stat:'true'});
        $scope.hrWingSetups = HrWingSetupByStatus.get({stat:'true'});
        $scope.load = function(id) {
            HrDepartmentSetup.get({id : id}, function(result) {
                $scope.hrDepartmentSetup = result;
            });
        };

        $scope.hremployeeinfos = [];
        HrEmployeeInfosByDesigLevel.get({desigLevel : 4}, function(result) {
            $scope.hremployeeinfos = result;
        });

        $scope.parentDeptId = 0;
        $scope.getDepartmentHeadList = function ()
        {
            if($stateParams.id)
            {
                console.log("Load head info list parent id: "+$stateParams.id);
                $scope.parentDeptId = $stateParams.id;
                HrDepartmentHeadInfoByDept.get({deptId : $stateParams.id}, function(result) {
                    console.log("totalResult: "+result.length);
                    $scope.hrDepartmentHeadInfos = result;
                });
            }
            else
            {
                $scope.parentDeptId = 0;
            }
            console.log("list parentWingId: "+$scope.parentDeptId);
        };

        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                    console.log("loggedInUser: "+JSON.stringify($scope.loggedInUser));
                    $scope.getDepartmentHeadList();
                });
            });
        };

        $scope.getLoggedInUser();

        $scope.orgCategoryList  = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});
        $scope.orgInfoList      = HrEmpWorkAreaDtlInfoByStatus.get({stat:'true'});
        $scope.orgInfoListFltr  = $scope.orgInfoList;

        $scope.instCategoryList     = InstCategory.query();
        $scope.instituteListAll     = Institute.query({size:500});
        $scope.instituteList        = $scope.instituteListAll;

        $scope.loadOrganizationInfoByCategory = function(orgCategory)
        {
            console.log(JSON.stringify(orgCategory)+", len: "+$scope.orgInfoList.length);
            $scope.orgInfoListFltr = [];
            angular.forEach($scope.orgInfoList,function(orgInfo)
            {
                if(orgCategory.id == orgInfo.workArea.id){
                    $scope.orgInfoListFltr.push(orgInfo);
                }
            });
        };

        $scope.loadInstituteByCategory = function(categoryInfo)
        {
            console.log(JSON.stringify(categoryInfo));
            /*InstituteByCategory.get({cat : categoryInfo.id}, function(result) {
                $scope.instituteList = result;
            });*/

            $scope.instituteList = [];
            angular.forEach($scope.instituteListAll,function(insitute)
            {
                if(insitute.instCategory != null)
                {
                    if(categoryInfo.id == insitute.instCategory.id){
                        $scope.instituteList.push(insitute);
                    }
                }
            });
        };

        $scope.departmentAlreadyExist = false;
        $scope.checkDepartmentUniqByCategory = function()
        {
            var refId = 0; var isValidData = false;
            if($scope.hrDepartmentSetup.organizationType=='Organization')
            {
                if($scope.hrDepartmentSetup.organizationInfo!=null)
                {
                    refId = $scope.hrDepartmentSetup.organizationInfo.id;
                    isValidData = true;
                }
            }
            else{
                if($scope.hrDepartmentSetup.institute!=null)
                {
                    refId = $scope.hrDepartmentSetup.institute.id;
                    isValidData = true;
                }
            }
            if($scope.hrDepartmentSetup.departmentInfo != null && isValidData == true)
            {
                $scope.editForm.departmentInfo.$pending = true;
                console.log("OrgId: "+$scope.hrDepartmentSetup.organizationType+", deptid: "+$scope.hrDepartmentSetup.departmentInfo.id+", refId: "+refId);

                HrDepartmentSetupUniqueness.get({orgtype:$scope.hrDepartmentSetup.organizationType, deptid: $scope.hrDepartmentSetup.departmentInfo.id, refid:refId}, function(result)
                {
                    console.log(JSON.stringify(result));
                    $scope.isSaving = !result.isValid;
                    $scope.editForm.departmentInfo.$pending = false;
                    if(result.isValid)
                    {
                        console.log("valid");
                        $scope.departmentAlreadyExist = false;
                    }
                    else
                    {
                        console.log("not valid");
                        $scope.departmentAlreadyExist = true;
                    }
                },function(response)
                {
                    console.log("data connection failed");
                    $scope.editForm.departmentInfo.$pending = false;
                });
            }
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrDepartmentSetupUpdate', result);
            $scope.isSaving = false;
            $state.go('hrDepartmentSetup');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrDepartmentSetup.updateBy = $scope.loggedInUser.id;
            $scope.hrDepartmentSetup.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.hrDepartmentSetup.id != null)
            {
                HrDepartmentSetup.update($scope.hrDepartmentSetup, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrDepartmentSetup.updated');
            }
            else
            {
                $scope.hrDepartmentSetup.createBy = $scope.loggedInUser.id;
                $scope.hrDepartmentSetup.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrDepartmentSetup.save($scope.hrDepartmentSetup, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrDepartmentSetup.created');
            }
        };

        $scope.clear = function() {

        };
}]);
