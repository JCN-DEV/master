'use strict';

angular.module('stepApp').controller('HrDepartmentHeadInfoDialogController',
    ['$scope', '$stateParams', '$rootScope', '$state', 'entity', 'HrDepartmentHeadInfo', 'HrDepartmentSetup', 'HrEmployeeInfosByDesigLevel','Principal','User','DateUtils','$window',
        function($scope, $stateParams, $rootScope, $state, entity, HrDepartmentHeadInfo, HrDepartmentSetup, HrEmployeeInfosByDesigLevel,Principal,User,DateUtils,$window) {

        $scope.hrDepartmentHeadInfo = entity;
        $scope.hrdepartmentsetups = HrDepartmentSetup.query();
        $scope.hremployeeinfos = [];//HrEmployeeInfo.query();
        $scope.load = function(id) {
            HrDepartmentHeadInfo.get({id : id}, function(result) {
                $scope.hrDepartmentHeadInfo = result;
            });
        };

        HrEmployeeInfosByDesigLevel.get({desigLevel : 3}, function(result) {
            $scope.hremployeeinfos = result;
        });

        $scope.loadDepartmentEntity = function ()
        {
            console.log("EntityId: "+entity.id);
            console.log("DeptIdStateParam: "+$stateParams.deptid);
            if($stateParams.deptid)
            {
                HrDepartmentSetup.get({id : $stateParams.deptid}, function(result) {
                    $scope.hrDepartmentHeadInfo.departmentInfo = result;
                    console.log("HeadDeptLoaded: "+$stateParams.deptid);
                });
            }
        };

        $scope.loggedInUser =   {};
        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                    $scope.loadDepartmentEntity();
                });
            });
        };
        $scope.getLoggedInUser();

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

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:hrDepartmentHeadInfoUpdate', result);
            $scope.isSaving = false;
            $window.history.back();
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrDepartmentHeadInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrDepartmentHeadInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.hrDepartmentHeadInfo.id != null)
            {
                HrDepartmentHeadInfo.update($scope.hrDepartmentHeadInfo, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.hrDepartmentHeadInfo.updated');
            } else
            {
                $scope.hrDepartmentHeadInfo.createBy = $scope.loggedInUser.id;
                $scope.hrDepartmentHeadInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrDepartmentHeadInfo.save($scope.hrDepartmentHeadInfo, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.hrDepartmentHeadInfo.created');
            }
        };

        $scope.clear = function() {

        };
}]);
