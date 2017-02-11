'use strict';

angular.module('stepApp').controller('HrEmpAuditObjectionInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'HrEmpAuditObjectionInfo', 'HrEmployeeInfo', 'MiscTypeSetupByCategory','User','Principal','DateUtils','HrEmployeeInfoByWorkArea',
        function($scope, $rootScope, $stateParams, $state, entity, HrEmpAuditObjectionInfo, HrEmployeeInfo, MiscTypeSetupByCategory, User, Principal, DateUtils,HrEmployeeInfoByWorkArea) {

        $scope.hrEmpAuditObjectionInfo = entity;
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.misctypesetups = MiscTypeSetupByCategory.get({cat:'ObjectionType',stat:'true'});

        $scope.workAreaList     = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});
        $scope.load = function(id) {
            HrEmpAuditObjectionInfo.get({id : id}, function(result) {
                $scope.hrEmpAuditObjectionInfo = result;
            });
        };

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
            $scope.$emit('stepApp:hrEmpAuditObjectionInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrEmpAuditObjectionInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmpAuditObjectionInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpAuditObjectionInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrEmpAuditObjectionInfo.id != null)
            {
                $scope.hrEmpAuditObjectionInfo.logId = 0;
                $scope.hrEmpAuditObjectionInfo.logStatus = 0;
                HrEmpAuditObjectionInfo.update($scope.hrEmpAuditObjectionInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEmpAuditObjectionInfo.updated');
            }
            else
            {
                $scope.hrEmpAuditObjectionInfo.logId = 0;
                $scope.hrEmpAuditObjectionInfo.logStatus = 0;
                $scope.hrEmpAuditObjectionInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEmpAuditObjectionInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmpAuditObjectionInfo.save($scope.hrEmpAuditObjectionInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpAuditObjectionInfo.created');
            }
        };

        $scope.clear = function() {

        };
}]);
