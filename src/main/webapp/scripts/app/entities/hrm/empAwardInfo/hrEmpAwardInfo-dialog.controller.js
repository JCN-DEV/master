'use strict';

angular.module('stepApp').controller('HrEmpAwardInfoDialogController',
    ['$scope', '$rootScope','$sce', '$stateParams', '$state', 'DataUtils', 'entity', 'HrEmpAwardInfo', 'HrEmployeeInfoByWorkArea','User','Principal','DateUtils','MiscTypeSetupByCategory',
        function($scope, $rootScope, $sce, $stateParams, $state, DataUtils, entity, HrEmpAwardInfo, HrEmployeeInfoByWorkArea, User, Principal, DateUtils,MiscTypeSetupByCategory) {

        $scope.hrEmpAwardInfo   = entity;
        $scope.workAreaList     = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});
        $scope.hremployeeinfos  = [];
        $scope.load = function(id) {
            HrEmpAwardInfo.get({id : id}, function(result) {
                $scope.hrEmpAwardInfo = result;
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
            $scope.$emit('stepApp:hrEmpAwardInfoUpdate', result);
            $scope.isSaving = false;
            console.log("TrainingSave: "+JSON.stringify(result));
            $state.go('hrEmpAwardInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmpAwardInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpAwardInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrEmpAwardInfo.id != null)
            {
                $scope.hrEmpAwardInfo.logId = 0;
                $scope.hrEmpAwardInfo.logStatus = 6;
                HrEmpAwardInfo.update($scope.hrEmpAwardInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEmpAwardInfo.updated');
            }
            else
            {
                $scope.hrEmpAwardInfo.logId = 0;
                $scope.hrEmpAwardInfo.logStatus = 6;
                $scope.hrEmpAwardInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEmpAwardInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmpAwardInfo.save($scope.hrEmpAwardInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpAwardInfo.created');
            }
        };

        $scope.clear = function() {

        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setGoOrderDoc = function ($file, hrEmpAwardInfo)
        {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function()
                    {
                        hrEmpAwardInfo.goOrderDoc = base64Data;
                        hrEmpAwardInfo.goOrderDocContentType = $file.type;
                        if (hrEmpAwardInfo.goOrderDocName == null)
                        {
                            hrEmpAwardInfo.goOrderDocName = $file.name;
                        }
                    });
                };
            }
        };

        $scope.setCertDoc = function ($file, hrEmpAwardInfo)
        {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function()
                    {
                        hrEmpAwardInfo.certDoc = base64Data;
                        hrEmpAwardInfo.certDocContentType = $file.type;
                        if (hrEmpAwardInfo.certDocName == null)
                        {
                            hrEmpAwardInfo.certDocName = $file.name;
                        }
                    });
                };
            }
        };

        $scope.previewGODoc = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.goOrderDoc, modelInfo.goOrderDocContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.goOrderDocContentType;
            $rootScope.viewerObject.pageTitle = "Employee Award GO Order Document";
            $rootScope.showPreviewModal();
        };

        $scope.previewCertDoc = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.certDoc, modelInfo.certDocContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.certDocContentType;
            $rootScope.viewerObject.pageTitle = "Employee Award Certificate Document";
            $rootScope.showPreviewModal();
        };
}]);
