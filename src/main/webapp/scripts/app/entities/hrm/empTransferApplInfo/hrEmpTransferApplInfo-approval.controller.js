'use strict';

angular.module('stepApp').controller('HrEmpTransferApplInfoApprovalController',
    ['$scope', '$state', '$stateParams', 'entity','$modalInstance', '$timeout', 'HrEmpTransferApplInfo', 'HrEmployeeInfo', 'MiscTypeSetupByCategory', 'HrEmpWorkAreaDtlInfo', 'HrDesignationSetup','HrEmployeeInfoByWorkArea','Principal','User','DateUtils','HrEmpServiceHistoryByEmployee',
        function($scope, $state, $stateParams, entity, $modalInstance, $timeout, HrEmpTransferApplInfo, HrEmployeeInfo, MiscTypeSetupByCategory, HrEmpWorkAreaDtlInfo, HrDesignationSetup,HrEmployeeInfoByWorkArea, Principal,User,DateUtils,HrEmpServiceHistoryByEmployee) {

        $scope.hrEmpTransferApplInfo = entity;
        $scope.hrEmpTransferApplInfoTemp = $scope.hrEmpTransferApplInfo;
        $scope.hremployeeinfos = [];//HrEmployeeInfo.query();
        $scope.hrempworkareadtlinfos = [];//HrEmpWorkAreaDtlInfo.query();
        $scope.orgNameFilterListOne = $scope.hrempworkareadtlinfos;
        $scope.orgNameFilterListTwo = $scope.hrempworkareadtlinfos;
        $scope.orgNameFilterListThree = $scope.hrempworkareadtlinfos;
        $scope.empServiceHistoryList = [];

        $timeout(function()
        {
            console.log('EmpId '+$scope.hrEmpTransferApplInfo.employeeInfo.id);
            HrEmpServiceHistoryByEmployee.query({empId:$scope.hrEmpTransferApplInfo.employeeInfo.id}, function(result) {
                $scope.empServiceHistoryList = result;
                $scope.hrEmpTransferApplInfo.isApproved = true;
            });
        }, 2000);

        $scope.hrdesignationsetups = HrDesignationSetup.query();
        $scope.load = function(id) {
            HrEmpTransferApplInfo.get({id : id}, function(result) {
                $scope.hrEmpTransferApplInfo = result;
            });
        };

        $scope.hremployeeinfos  = [];
        $scope.workAreaList  = [];//MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});

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
            $scope.$emit('stepApp:hrEmpTransferApplInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrm', {}, { reload: true });
            $modalInstance.dismiss('cancel');
            //$state.transitionTo('hrm', null, {'reload':true});
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmpTransferApplInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpTransferApplInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            //console.log(JSON.stringify($scope.hrEmpTransferApplInfo));

            if($scope.hrEmpTransferApplInfo.selectedOption=='1')
            {
                console.log("One selected");
            }
            else if($scope.hrEmpTransferApplInfo.selectedOption=='2')
            {
                console.log("Two selected");
            }
            else if($scope.hrEmpTransferApplInfo.selectedOption=='3')
            {
                console.log("Three selected");
            }

            $scope.hrEmpTransferApplInfo.logStatus = 8;
            if($scope.hrEmpTransferApplInfo.isApproved)
            {
                $scope.hrEmpTransferApplInfo.logStatus = 7;
            }
            else
            {
                $scope.hrEmpTransferApplInfo.selectedOption = 0;
            }
            console.log("Selected: "+$scope.hrEmpTransferApplInfo.selectedOption+", approved: "+$scope.hrEmpTransferApplInfo.isApproved+", logStat: "+$scope.hrEmpTransferApplInfo.logStatus);
            if ($scope.hrEmpTransferApplInfo.id != null)
            {
                //$scope.hrEmpTransferApplInfo.logId = 0;
                //$scope.hrEmpTransferApplInfo.logStatus = 6;
                HrEmpTransferApplInfo.update($scope.hrEmpTransferApplInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
