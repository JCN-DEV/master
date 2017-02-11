'use strict';

angular.module('stepApp').controller('HrEmpPreGovtJobInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'HrEmpPreGovtJobInfo', 'HrEmployeeInfoByWorkArea','User','Principal','DateUtils','MiscTypeSetupByCategory',
        function($scope, $rootScope, $stateParams, $state, entity, HrEmpPreGovtJobInfo, HrEmployeeInfoByWorkArea, User, Principal, DateUtils,MiscTypeSetupByCategory) {

        $scope.hrEmpPreGovtJobInfo = entity;
        $scope.load = function(id) {
            HrEmpPreGovtJobInfo.get({id : id}, function(result) {
                $scope.hrEmpPreGovtJobInfo = result;
            });
        };

        $scope.hremployeeinfos  = [];
        $scope.workAreaList     = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});

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
            $scope.$emit('stepApp:hrEmpPreGovtJobInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrEmpPreGovtJobInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmpPreGovtJobInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpPreGovtJobInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrEmpPreGovtJobInfo.id != null)
            {
                $scope.hrEmpPreGovtJobInfo.logId = 0;
                $scope.hrEmpPreGovtJobInfo.logStatus = 6;
                HrEmpPreGovtJobInfo.update($scope.hrEmpIncrementInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEmpIncrementInfo.updated');
            }
            else
            {
                $scope.hrEmpPreGovtJobInfo.logId = 0;
                $scope.hrEmpPreGovtJobInfo.logStatus = 6;
                $scope.hrEmpPreGovtJobInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEmpPreGovtJobInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmpPreGovtJobInfo.save($scope.hrEmpPreGovtJobInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpPreGovtJobInfo.created');
            }
        };

        $scope.clear = function() {

        };
}]);
