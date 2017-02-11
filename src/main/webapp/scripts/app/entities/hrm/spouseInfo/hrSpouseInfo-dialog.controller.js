'use strict';

angular.module('stepApp').controller('HrSpouseInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'HrSpouseInfo', 'HrEmployeeInfoByWorkArea','Principal','User','DateUtils','MiscTypeSetupByCategory',
        function($scope, $rootScope, $stateParams, $state, entity, HrSpouseInfo, HrEmployeeInfoByWorkArea, Principal, User, DateUtils,MiscTypeSetupByCategory) {

        $scope.hrSpouseInfo = entity;
        $scope.load = function(id) {
            HrSpouseInfo.get({id : id}, function(result) {
                $scope.hrSpouseInfo = result;
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

        //$scope.phoneNumbr = /^\+?\d{11}?\d{13}$/;

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrSpouseInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrSpouseInfo');
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
                    $scope.hrSpouseInfo.updateBy = result.id;
                    $scope.hrSpouseInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

                    if ($scope.hrSpouseInfo.id != null)
                    {
                        $scope.hrSpouseInfo.logId = 0;
                        $scope.hrSpouseInfo.logStatus = 6;
                        HrSpouseInfo.update($scope.hrSpouseInfo, onSaveSuccess, onSaveError);
                        $rootScope.setWarningMessage('stepApp.hrSpouseInfo.updated');
                    }
                    else
                    {
                        $scope.hrSpouseInfo.logId = 0;
                        $scope.hrSpouseInfo.logStatus = 6;
                        $scope.hrSpouseInfo.createBy = result.id;
                        $scope.hrSpouseInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                        HrSpouseInfo.save($scope.hrSpouseInfo, onSaveSuccess, onSaveError);
                        $rootScope.setSuccessMessage('stepApp.hrSpouseInfo.created');
                    }
                });
            });
        };

        $scope.clear = function() {

        };
}]);
