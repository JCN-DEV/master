'use strict';

angular.module('stepApp').controller('HrEmpProfMemberInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'HrEmpProfMemberInfo', 'HrEmployeeInfoByWorkArea','User','Principal','DateUtils','MiscTypeSetupByCategory',
        function($scope, $rootScope, $stateParams, $state, entity, HrEmpProfMemberInfo, HrEmployeeInfoByWorkArea, User, Principal, DateUtils,MiscTypeSetupByCategory) {

        $scope.hrEmpProfMemberInfo = entity;
        $scope.load = function(id) {
            HrEmpProfMemberInfo.get({id : id}, function(result) {
                $scope.hrEmpProfMemberInfo = result;
            });
        };

        $scope.hremployeeinfos  = [];
        $scope.workAreaList     = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});
        $scope.memberCatList    = MiscTypeSetupByCategory.get({cat:'MembershipCategory',stat:'true'});

        $scope.loadModelByWorkArea = function(workArea)
        {
            HrEmployeeInfoByWorkArea.get({areaid : workArea.id}, function(result) {
                $scope.hremployeeinfos = result;
                console.log("Total record: "+result.length);
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
            $scope.$emit('stepApp:hrEmpProfMemberInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrEmpProfMemberInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmpProfMemberInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpProfMemberInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrEmpProfMemberInfo.id != null)
            {
                $scope.hrEmpProfMemberInfo.logId = 0;
                $scope.hrEmpProfMemberInfo.logStatus = 6;
                HrEmpProfMemberInfo.update($scope.hrEmpProfMemberInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEmpProfMemberInfo.updated');
            }
            else
            {
                $scope.hrEmpProfMemberInfo.logId = 0;
                $scope.hrEmpProfMemberInfo.logStatus = 6;
                $scope.hrEmpProfMemberInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEmpProfMemberInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmpProfMemberInfo.save($scope.hrEmpProfMemberInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpProfMemberInfo.created');
            }
        };

        $scope.clear = function() {

        };
}]);
