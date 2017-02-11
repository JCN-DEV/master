'use strict';

angular.module('stepApp').controller('HrEmpTransferApplInfoDialogController',
    ['$scope','$rootScope', '$state', '$stateParams', 'entity', 'HrEmpTransferApplInfo', 'HrEmployeeInfo', 'MiscTypeSetupByCategory', 'HrEmpWorkAreaDtlInfo', 'HrDesignationSetup','HrEmployeeInfoByWorkArea','Principal','User','DateUtils',
        function($scope,$rootScope, $state, $stateParams, entity, HrEmpTransferApplInfo, HrEmployeeInfo, MiscTypeSetupByCategory, HrEmpWorkAreaDtlInfo, HrDesignationSetup,HrEmployeeInfoByWorkArea, Principal,User,DateUtils) {

        $scope.hrEmpTransferApplInfo = entity;
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
            angular.forEach($scope.workAreaList,function(orgNameObj)
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
            $state.go('hrEmpTransferApplInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmpTransferApplInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpTransferApplInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrEmpTransferApplInfo.id != null)
            {
                $scope.hrEmpTransferApplInfo.logId = 0;
                $scope.hrEmpTransferApplInfo.logStatus = 6;
                HrEmpTransferApplInfo.update($scope.hrEmpTransferApplInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEmpTransferApplInfo.update');
            }
            else
            {
                $scope.hrEmpTransferApplInfo.selectedOption = 0;
                $scope.hrEmpTransferApplInfo.logId = 0;
                $scope.hrEmpTransferApplInfo.logStatus = 6;
                $scope.hrEmpTransferApplInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEmpTransferApplInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmpTransferApplInfo.save($scope.hrEmpTransferApplInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpTransferApplInfo.save');
            }
        };

        $scope.clear = function() {

        };
}]);
