'use strict';

angular.module('stepApp').controller('HrEmpActingDutyInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$sce', '$state', 'DataUtils', 'entity', 'HrEmpActingDutyInfo', 'HrEmployeeInfo','User','Principal','DateUtils',
        function($scope, $rootScope, $stateParams, $sce, $state, DataUtils, entity, HrEmpActingDutyInfo, HrEmployeeInfo, User, Principal, DateUtils) {

        $scope.hrEmpActingDutyInfo = entity;
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.load = function(id) {
            HrEmpActingDutyInfo.get({id : id}, function(result) {
                $scope.hrEmpActingDutyInfo = result;
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
            $scope.$emit('stepApp:hrEmpActingDutyInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrEmpActingDutyInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmpActingDutyInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpActingDutyInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrEmpActingDutyInfo.id != null)
            {
                $scope.hrEmpActingDutyInfo.logId = 0;
                $scope.hrEmpActingDutyInfo.logStatus = 2;
                HrEmpActingDutyInfo.update($scope.hrEmpActingDutyInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEmpActingDutyInfo.updated');
            }
            else
            {
                $scope.hrEmpActingDutyInfo.logId = 0;
                $scope.hrEmpActingDutyInfo.logStatus = 1;
                $scope.hrEmpActingDutyInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEmpActingDutyInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmpActingDutyInfo.save($scope.hrEmpActingDutyInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpActingDutyInfo.created');
            }
        };

        $scope.clear = function() {

        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setGoDoc = function ($file, hrEmpActingDutyInfo) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        hrEmpActingDutyInfo.goDoc = base64Data;
                        hrEmpActingDutyInfo.goDocContentType = $file.type;
                        if (hrEmpActingDutyInfo.goDocName == null)
                        {
                            hrEmpActingDutyInfo.goDocName = $file.name;
                        }
                    });
                };
            }
        };

        $scope.previewImage = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.goDoc, modelInfo.goDocContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.goDocContentType;
            $rootScope.viewerObject.pageTitle = "Employee Acting Duty Document - "+modelInfo.toInstitution;
            $rootScope.showPreviewModal();
        };
}]);
