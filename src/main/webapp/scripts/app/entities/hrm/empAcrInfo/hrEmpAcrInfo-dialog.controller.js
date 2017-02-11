'use strict';

angular.module('stepApp').controller('HrEmpAcrInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'HrEmpAcrInfo', 'HrEmployeeInfo','User','Principal','DateUtils','HrEmployeeInfoByWorkArea','MiscTypeSetupByCategory',
        function($scope, $rootScope, $stateParams, $state, entity, HrEmpAcrInfo, HrEmployeeInfo, User, Principal, DateUtils,HrEmployeeInfoByWorkArea,MiscTypeSetupByCategory) {

        $scope.hrEmpAcrInfo = entity;
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.load = function(id) {
            HrEmpAcrInfo.get({id : id}, function(result) {
                $scope.hrEmpAcrInfo = result;
            });
        };

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
            $scope.$emit('stepApp:hrEmpAcrInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrEmpAcrInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmpAcrInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpAcrInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrEmpAcrInfo.id != null)
            {
                $scope.hrEmpAcrInfo.logId = 0;
                $scope.hrEmpAcrInfo.logStatus = 2;
                HrEmpAcrInfo.update($scope.hrEmpAcrInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEmpAcrInfo.updated');
            }
            else
            {
                $scope.hrEmpAcrInfo.logId = 0;
                $scope.hrEmpAcrInfo.logStatus = 1;
                $scope.hrEmpAcrInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEmpAcrInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmpAcrInfo.save($scope.hrEmpAcrInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpAcrInfo.created');
            }
        };

        $scope.clear = function() {

        };
}]);
