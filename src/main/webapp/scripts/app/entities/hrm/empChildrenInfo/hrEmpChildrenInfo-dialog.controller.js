'use strict';

angular.module('stepApp').controller('HrEmpChildrenInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'HrEmpChildrenInfo', 'HrEmployeeInfo','Principal','User','DateUtils','MiscTypeSetupByCategory','HrEmployeeInfoByWorkArea',
        function($scope, $rootScope, $stateParams, $state, entity, HrEmpChildrenInfo, HrEmployeeInfo, Principal, User, DateUtils,MiscTypeSetupByCategory,HrEmployeeInfoByWorkArea) {

        $scope.hrEmpChildrenInfo = entity;
        $scope.hremployeeinfos  = [];
        $scope.workAreaList     = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});
        $scope.load = function(id) {
            HrEmpChildrenInfo.get({id : id}, function(result) {
                $scope.hrEmpChildrenInfo = result;
            });
        };

        $scope.loadModelByWorkArea = function(workArea)
        {
            HrEmployeeInfoByWorkArea.get({areaid : workArea.id}, function(result) {
                $scope.hremployeeinfos = result;
                console.log("Total record: "+result.length);
            });
        };

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
            $scope.$emit('stepApp:hrEmpChildrenInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrEmpChildrenInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.isSaving = true;
                    $scope.hrEmpChildrenInfo.updateBy = result.id;
                    $scope.hrEmpChildrenInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                    $scope.hrEmpChildrenInfo.logStatus = 6;
                    if ($scope.hrEmpChildrenInfo.id != null)
                    {
                        HrEmpChildrenInfo.update($scope.hrEmpChildrenInfo, onSaveSuccess, onSaveError);
                        $rootScope.setWarningMessage('stepApp.hrEmpChildrenInfo.updated');
                    }
                    else
                    {
                        $scope.hrEmpChildrenInfo.logId = 0;
                        $scope.hrEmpChildrenInfo.createBy = result.id;
                        $scope.hrEmpChildrenInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                        HrEmpChildrenInfo.save($scope.hrEmpChildrenInfo, onSaveSuccess, onSaveError);
                        $rootScope.setSuccessMessage('stepApp.hrEmpChildrenInfo.created');
                    }
                });
            });
        };

        $scope.clear = function() {

        };
}]);
