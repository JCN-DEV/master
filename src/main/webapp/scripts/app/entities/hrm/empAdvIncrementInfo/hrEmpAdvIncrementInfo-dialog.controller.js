'use strict';

angular.module('stepApp').controller('HrEmpAdvIncrementInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'DataUtils', 'entity', 'HrEmpAdvIncrementInfo', 'HrEmployeeInfo', 'MiscTypeSetupByCategory','User','Principal','DateUtils',
        function($scope, $rootScope, $stateParams, $state, DataUtils, entity, HrEmpAdvIncrementInfo, HrEmployeeInfo, MiscTypeSetupByCategory, User, Principal, DateUtils) {

        $scope.hrEmpAdvIncrementInfo = entity;
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.misctypesetups = MiscTypeSetupByCategory.get({cat:'JobCategory',stat:'true'});
        $scope.load = function(id) {
            HrEmpAdvIncrementInfo.get({id : id}, function(result) {
                $scope.hrEmpAdvIncrementInfo = result;
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
            $scope.$emit('stepApp:hrEmpAdvIncrementInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrEmpAdvIncrementInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmpAdvIncrementInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpAdvIncrementInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrEmpAdvIncrementInfo.id != null)
            {
                $scope.hrEmpAdvIncrementInfo.logId = 0;
                $scope.hrEmpAdvIncrementInfo.logStatus = 0;
                HrEmpAdvIncrementInfo.update($scope.hrEmpAdvIncrementInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEmpAdvIncrementInfo.updated');
            }
            else
            {
                $scope.hrEmpAdvIncrementInfo.logId = 0;
                $scope.hrEmpAdvIncrementInfo.logStatus = 0;
                $scope.hrEmpAdvIncrementInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEmpAdvIncrementInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmpAdvIncrementInfo.save($scope.hrEmpAdvIncrementInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpAdvIncrementInfo.created');
            }
        };

        $scope.clear = function() {
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setGoDoc = function ($file, hrEmpAdvIncrementInfo) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        hrEmpAdvIncrementInfo.goDoc = base64Data;
                        hrEmpAdvIncrementInfo.goDocContentType = $file.type;
                        if (hrEmpAdvIncrementInfo.goDocName == null)
                        {
                            hrEmpAdvIncrementInfo.goDocName = $file.name;
                        }
                    });
                };
            }
        };
}]);
