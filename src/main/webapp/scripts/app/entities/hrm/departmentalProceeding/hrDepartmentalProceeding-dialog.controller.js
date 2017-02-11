'use strict';

angular.module('stepApp').controller('HrDepartmentalProceedingDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'DataUtils', 'entity', 'HrDepartmentalProceeding', 'HrEmployeeInfo','User','Principal','DateUtils','HrEmployeeInfoByWorkArea','MiscTypeSetupByCategory',
        function($scope, $rootScope, $stateParams, $state, DataUtils, entity, HrDepartmentalProceeding, HrEmployeeInfo, User, Principal, DateUtils,HrEmployeeInfoByWorkArea,MiscTypeSetupByCategory) {

        $scope.hrDepartmentalProceeding = entity;
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.load = function(id) {
            HrDepartmentalProceeding.get({id : id}, function(result) {
                $scope.hrDepartmentalProceeding = result;
            });
        };

        $scope.punisTypeList    = MiscTypeSetupByCategory.get({cat:'PunishmentType',stat:'true'});
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

        var onSaveSuccess = function (result)
        {
            $scope.$emit('stepApp:hrDepartmentalProceedingUpdate', result);
            $scope.isSaving = false;
            $state.go('hrDepartmentalProceeding');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrDepartmentalProceeding.updateBy = $scope.loggedInUser.id;
            $scope.hrDepartmentalProceeding.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrDepartmentalProceeding.id != null)
            {
                $scope.hrDepartmentalProceeding.logId = 0;
                $scope.hrDepartmentalProceeding.logStatus = 2;
                HrDepartmentalProceeding.update($scope.hrDepartmentalProceeding, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrDepartmentalProceeding.updated');
            }
            else
            {
                $scope.hrDepartmentalProceeding.logId = 0;
                $scope.hrDepartmentalProceeding.logStatus = 1;
                $scope.hrDepartmentalProceeding.createBy = $scope.loggedInUser.id;
                $scope.hrDepartmentalProceeding.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrDepartmentalProceeding.save($scope.hrDepartmentalProceeding, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrDepartmentalProceeding.created');
            }
        };

        $scope.clear = function() {

        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setGoDoc = function ($file, hrDepartmentalProceeding)
        {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        hrDepartmentalProceeding.goDoc = base64Data;
                        hrDepartmentalProceeding.goDocContentType = $file.type;
                        if (hrDepartmentalProceeding.goDocName == null)
                        {
                            hrDepartmentalProceeding.goDocName = $file.name;
                        }
                    });
                };
            }
        };
}]);
