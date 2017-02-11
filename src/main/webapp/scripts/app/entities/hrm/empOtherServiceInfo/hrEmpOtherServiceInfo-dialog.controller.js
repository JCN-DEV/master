'use strict';

angular.module('stepApp').controller('HrEmpOtherServiceInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'HrEmpOtherServiceInfo', 'HrEmployeeInfoByWorkArea','User','Principal','DateUtils','MiscTypeSetupByCategory',
        function($scope, $rootScope, $stateParams, $state, entity, HrEmpOtherServiceInfo, HrEmployeeInfoByWorkArea, User, Principal, DateUtils,MiscTypeSetupByCategory)
        {

        $scope.hrEmpOtherServiceInfo = entity;
        $scope.load = function(id) {
            HrEmpOtherServiceInfo.get({id : id}, function(result) {
                $scope.hrEmpOtherServiceInfo = result;
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
            $scope.$emit('stepApp:hrEmpOtherServiceInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrEmpOtherServiceInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmpOtherServiceInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpOtherServiceInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrEmpOtherServiceInfo.id != null)
            {
                $scope.hrEmpOtherServiceInfo.logId = 0;
                $scope.hrEmpOtherServiceInfo.logStatus = 6;
                HrEmpOtherServiceInfo.update($scope.hrEmpOtherServiceInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEmpOtherServiceInfo.updated');
            }
            else
            {
                $scope.hrEmpOtherServiceInfo.logId = 0;
                $scope.hrEmpOtherServiceInfo.logStatus = 6;
                $scope.hrEmpOtherServiceInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEmpOtherServiceInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmpOtherServiceInfo.save($scope.hrEmpOtherServiceInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpOtherServiceInfo.created');
            }
        };

        $scope.clear = function() {

        };
}]);
