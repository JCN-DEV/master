'use strict';

angular.module('stepApp').controller('HrEmpTransferInfoDialogController',
    ['$scope', '$rootScope', '$sce', '$stateParams', '$state', 'DataUtils', 'entity', 'HrEmpTransferInfo', 'HrEmployeeInfoByWorkArea', 'MiscTypeSetupByCategory','User','Principal','DateUtils',
        function($scope, $rootScope, $sce, $stateParams, $state, DataUtils, entity, HrEmpTransferInfo, HrEmployeeInfoByWorkArea, MiscTypeSetupByCategory, User, Principal, DateUtils) {

        $scope.hrEmpTransferInfo = entity;
        $scope.transferTypeList = MiscTypeSetupByCategory.get({cat:'TransferType',stat:'true'});
        $scope.jobCategoryList = MiscTypeSetupByCategory.get({cat:'JobCategory',stat:'true'});
        $scope.load = function(id) {
            HrEmpTransferInfo.get({id : id}, function(result) {
                $scope.hrEmpTransferInfo = result;
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
            $scope.$emit('stepApp:hrEmpTransferInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrEmpTransferInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmpTransferInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpTransferInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrEmpTransferInfo.id != null)
            {
                $scope.hrEmpTransferInfo.logId = 0;
                $scope.hrEmpTransferInfo.logStatus = 6;
                HrEmpTransferInfo.update($scope.hrEmpTransferInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEmpTransferInfo.updated');
            }
            else
            {
                $scope.hrEmpTransferInfo.logId = 0;
                $scope.hrEmpTransferInfo.logStatus = 6;
                $scope.hrEmpTransferInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEmpTransferInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmpTransferInfo.save($scope.hrEmpTransferInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpTransferInfo.created');
            }
        };

        $scope.clear = function() {

        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setGoDoc = function ($file, hrEmpTransferInfo) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        hrEmpTransferInfo.goDoc = base64Data;
                        hrEmpTransferInfo.goDocContentType = $file.type;
                        if (hrEmpTransferInfo.goDocName == null)
                        {
                            hrEmpTransferInfo.goDocName = $file.name;
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
            $rootScope.viewerObject.pageTitle = "Employee Transfer Document";
            $rootScope.showPreviewModal();
        };
}]);
