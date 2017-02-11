'use strict';

angular.module('stepApp').controller('PrlAllowDeductInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'PrlAllowDeductInfo', 'HrGradeSetupByStatus','User','Principal','DateUtils','PrlAllowDeductInfoUniqueness','PrlAllowDeductInfoByCategoryCheck',
        function($scope, $rootScope, $stateParams, $state, entity, PrlAllowDeductInfo, HrGradeSetupByStatus, User, Principal, DateUtils,PrlAllowDeductInfoUniqueness,PrlAllowDeductInfoByCategoryCheck) {

        $scope.prlAllowDeductInfo = entity;
        $scope.hrgradesetups = HrGradeSetupByStatus.get({stat:'true'});
        $scope.load = function(id) {
            PrlAllowDeductInfo.get({id : id}, function(result) {
                $scope.prlAllowDeductInfo = result;
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

        $scope.allowDeducAlreadyExist = false;
        $scope.checkAllowDeduUniqByNameAndType = function()
        {
            console.log("DistId: "+$scope.prlAllowDeductInfo.allowDeducType+", name: "+$scope.prlAllowDeductInfo.name)
            if($scope.prlAllowDeductInfo.allowDeducType !=null && $scope.prlAllowDeductInfo.name != null && $scope.prlAllowDeductInfo.name.length > 0)
            {
                $scope.editForm.name.$pending = true;
                PrlAllowDeductInfoUniqueness.get({type:$scope.prlAllowDeductInfo.allowDeducType, name: $scope.prlAllowDeductInfo.name}, function(result)
                {
                    //console.log(JSON.stringify(result));
                    $scope.isSaving = !result.isValid;
                    $scope.editForm.name.$pending = false;
                    if(result.isValid)
                    {
                        console.log("valid");
                        $scope.allowDeducAlreadyExist = false;
                    }
                    else
                    {
                        console.log("not valid");
                        $scope.allowDeducAlreadyExist = true;
                    }
                },function(response)
                {
                    console.log("data connection failed");
                    $scope.editForm.name.$pending = false;
                });
            }
        };

        $scope.allowDeducCategoryAlreadyExist = false;
        $scope.checkAllowDeduCategoryUniqueness = function()
        {
            var alid = 0;
            if($scope.prlAllowDeductInfo.id != null)
            {
                alid = $scope.prlAllowDeductInfo.id;
            }
            console.log("Category: "+$scope.prlAllowDeductInfo.allowCategory+", id: "+alid);

            if($scope.prlAllowDeductInfo.allowCategory !=null && $scope.prlAllowDeductInfo.allowCategory != 'AD')
            {
                $scope.editForm.allowCategory.$pending = true;
                PrlAllowDeductInfoByCategoryCheck.get({cat:$scope.prlAllowDeductInfo.allowCategory,alid:alid}, function(result)
                {
                    console.log(JSON.stringify(result));
                    $scope.isSaving = result.isExist;
                    $scope.editForm.allowCategory.$pending = false;
                    if(result.isExist)
                    {
                        console.log("already exist");
                        $scope.allowDeducCategoryAlreadyExist = true;
                    }
                    else
                    {
                        console.log("new, not exist");
                        $scope.allowDeducCategoryAlreadyExist = false;
                    }
                },function(response)
                {
                    console.log("data connection failed");
                    $scope.editForm.allowCategory.$pending = false;
                });
            }
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:prlAllowDeductInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('prlAllowDeductInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.prlAllowDeductInfo.updateBy = $scope.loggedInUser.id;
            $scope.prlAllowDeductInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if($scope.prlAllowDeductInfo.allowDeducType === 'OnetimeAllowance')
            {
                $scope.prlAllowDeductInfo.gradeInfo = null;
            }

            if ($scope.prlAllowDeductInfo.id != null)
            {
                PrlAllowDeductInfo.update($scope.prlAllowDeductInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.prlAllowDeductInfo.updated');
            }
            else
            {
                $scope.prlAllowDeductInfo.createBy = $scope.loggedInUser.id;
                $scope.prlAllowDeductInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PrlAllowDeductInfo.save($scope.prlAllowDeductInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.prlAllowDeductInfo.created');
            }
        };

        $scope.clear = function() {

        };
}]);
