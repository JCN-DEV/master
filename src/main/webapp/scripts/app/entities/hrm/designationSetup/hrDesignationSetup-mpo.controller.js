'use strict';

angular.module('stepApp').controller('HrDesignationSetupMpoController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'HrDesignationSetup','Principal','User','DateUtils','MiscTypeSetupByCategory','HrEmpWorkAreaDtlInfoByStatus','HrGradeSetupByStatus','HrDesignationSetupMpoUniqueness','HrDesignationHeadSetupByStatus','InstituteByCategory','InstCategory','Institute','InstLevel',
        function($scope, $rootScope, $stateParams, $state, entity, HrDesignationSetup, Principal, User, DateUtils,MiscTypeSetupByCategory,HrEmpWorkAreaDtlInfoByStatus,HrGradeSetupByStatus,HrDesignationSetupMpoUniqueness,HrDesignationHeadSetupByStatus,InstituteByCategory,InstCategory,Institute,InstLevel) {

        $scope.hrDesignationSetup = entity;
        $scope.instLevels = InstLevel.query();
        //$scope.designationHeadList  = HrDesignationHeadSetupByType.get({type:'HRM'});


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

        $scope.designationHeadList  = HrDesignationHeadSetupByStatus.get({stat:'true'});
        $scope.designationHeadListFiltr = $scope.designationHeadList;

        $scope.gradeInfoList    = HrGradeSetupByStatus.get({stat:'true'});

        $scope.instCategoryList     = InstCategory.query();

        $scope.filterDesignationByType = function()
        {
            console.log("desigtype:"+$scope.hrDesignationSetup.desigType);
            $scope.designationHeadListFiltr = [];
            if($scope.hrDesignationSetup.desigType!=null)
            {
                angular.forEach($scope.designationHeadList,function(desigInfo)
                {
                    if(desigInfo.desigType == $scope.hrDesignationSetup.desigType){
                        $scope.designationHeadListFiltr.push(desigInfo);
                    }
                });
            }

        };

        $scope.designationAlreadyExist = false;
        $scope.checkDesignationUniqByTypeLevelCat = function()
        {
            console.log("desigType: "+$scope.hrDesignationSetup.desigType);
            if($scope.hrDesignationSetup.desigType != null
                && $scope.hrDesignationSetup.instCategory != null
                && $scope.hrDesignationSetup.instLevel != null)
            {
                $scope.editForm.designationInfo.$pending = true;
                console.log("dtype: "+$scope.hrDesignationSetup.desigType+", lvlid: "+$scope.hrDesignationSetup.instLevel.id+", catid: "+$scope.hrDesignationSetup.instCategory.id);

                HrDesignationSetupMpoUniqueness.get({dtype:$scope.hrDesignationSetup.desigType, lvlid: $scope.hrDesignationSetup.instLevel.id, catId:$scope.hrDesignationSetup.instCategory.id}, function(result)
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
