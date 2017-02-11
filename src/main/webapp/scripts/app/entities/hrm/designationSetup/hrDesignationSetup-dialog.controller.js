'use strict';

angular.module('stepApp').controller('HrDesignationSetupDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'HrDesignationSetup','Principal','User','DateUtils','MiscTypeSetupByCategory','HrEmpWorkAreaDtlInfoByStatus','HrGradeSetupByStatus','HrDesignationSetupUniqueness','HrDesignationHeadSetupByType','InstituteByCategory','InstCategory','Institute',
        function($scope, $rootScope, $stateParams, $state, entity, HrDesignationSetup, Principal, User, DateUtils,MiscTypeSetupByCategory,HrEmpWorkAreaDtlInfoByStatus,HrGradeSetupByStatus,HrDesignationSetupUniqueness,HrDesignationHeadSetupByType,InstituteByCategory,InstCategory,Institute) {

        $scope.hrDesignationSetup = entity;
        //$scope.organizationCategoryList = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});
        //$scope.designationHeadList  = HrDesignationHeadSetupByStatus.get({stat:'true'});
        $scope.designationHeadList  = HrDesignationHeadSetupByType.get({type:'HRM'});
        $scope.load = function(id) {
            HrDesignationSetup.get({id : id}, function(result) {
                $scope.hrDesignationSetup = result;
            });
        };

        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                    console.log("loggedInUser: "+JSON.stringify($scope.loggedInUser));
                });
            });
        };
        $scope.getLoggedInUser();

        $scope.orgCategoryList  = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});
        $scope.gradeInfoList    = HrGradeSetupByStatus.get({stat:'true'});
        $scope.orgInfoList      = HrEmpWorkAreaDtlInfoByStatus.get({stat:'true'});
        $scope.orgInfoListFltr  = $scope.orgInfoList;

        $scope.instCategoryList     = InstCategory.query();
        $scope.instituteListAll     = Institute.query({size:500});
        $scope.instituteList        = $scope.instituteListAll;

        $scope.loadOrganizationInfoByCategory = function(orgCategory)
        {
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

        $scope.designationAlreadyExist = false;
        $scope.checkDesignationUniqByCategory = function()
        {
            var refId = 0; var isValidData = false;
            if($scope.hrDesignationSetup.organizationType=='Organization')
            {
                if($scope.hrDesignationSetup.organizationInfo!=null)
                {
                    refId = $scope.hrDesignationSetup.organizationInfo.id;
                    isValidData = true;
                }
            }
            else{
                if($scope.hrDesignationSetup.institute!=null)
                {
                    refId = $scope.hrDesignationSetup.institute.id;
                    isValidData = true;
                }
            }

            if($scope.hrDesignationSetup.designationInfo != null && isValidData == true)
            {
                $scope.editForm.designationInfo.$pending = true;
                console.log("OrgId: "+$scope.hrDesignationSetup.organizationType+", desigid: "+$scope.hrDesignationSetup.designationInfo.id+", refId: "+refId);

                HrDesignationSetupUniqueness.get({orgtype:$scope.hrDesignationSetup.organizationType, desigid: $scope.hrDesignationSetup.designationInfo.id, refid:refId}, function(result)
                {
                    console.log(JSON.stringify(result));
                    $scope.isSaving = !result.isValid;
                    $scope.editForm.designationInfo.$pending = false;
                    if(result.isValid)
                    {
                        console.log("valid");
                        $scope.designationAlreadyExist = false;
                    }
                    else
                    {
                        console.log("not valid");
                        $scope.designationAlreadyExist = true;
                    }
                },function(response)
                {
                    console.log("data connection failed");
                    $scope.editForm.designationInfo.$pending = false;
                });
            }
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrDesignationSetupUpdate', result);
            $scope.isSaving = false;
            $state.go('hrDesignationSetup');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrDesignationSetup.updateBy = $scope.loggedInUser.id;
            $scope.hrDesignationSetup.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            console.log(JSON.stringify($scope.hrDesignationSetup));
            if ($scope.hrDesignationSetup.id != null)
            {
                HrDesignationSetup.update($scope.hrDesignationSetup, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrDesignationSetup.updated');
            }
            else
            {
                $scope.hrDesignationSetup.createBy = $scope.loggedInUser.id;
                $scope.hrDesignationSetup.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrDesignationSetup.save($scope.hrDesignationSetup, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrDesignationSetup.created');
            }
        };

        $scope.clear = function() {

        };
}]);
