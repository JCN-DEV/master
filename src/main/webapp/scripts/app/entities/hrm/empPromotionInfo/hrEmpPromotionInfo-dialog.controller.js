'use strict';

angular.module('stepApp').controller('HrEmpPromotionInfoDialogController',
    ['$scope', '$rootScope', '$sce', '$stateParams', '$state', 'DataUtils', 'entity', 'HrEmpPromotionInfo', 'HrEmployeeInfoByWorkArea', 'MiscTypeSetupByCategory','User','Principal','DateUtils',
        function($scope, $rootScope, $sce, $stateParams, $state, DataUtils, entity, HrEmpPromotionInfo, HrEmployeeInfoByWorkArea, MiscTypeSetupByCategory, User, Principal, DateUtils) {

        $scope.hrEmpPromotionInfo = entity;
        $scope.misctypesetups = MiscTypeSetupByCategory.get({cat:'JobCategory',stat:'true'});
        $scope.load = function(id) {
            HrEmpPromotionInfo.get({id : id}, function(result) {
                $scope.hrEmpPromotionInfo = result;
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
            $scope.$emit('stepApp:hrEmpPromotionInfoUpdate', result);
            $state.go('hrEmpPromotionInfo');
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmpPromotionInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpPromotionInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrEmpPromotionInfo.id != null)
            {
                $scope.hrEmpPromotionInfo.logId = 0;
                $scope.hrEmpPromotionInfo.logStatus = 6;
                HrEmpPromotionInfo.update($scope.hrEmpPromotionInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEmpPromotionInfo.updated');
            }
            else
            {
                $scope.hrEmpPromotionInfo.logId = 0;
                $scope.hrEmpPromotionInfo.logStatus = 6;
                $scope.hrEmpPromotionInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEmpPromotionInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmpPromotionInfo.save($scope.hrEmpPromotionInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpPromotionInfo.created');
            }
        };

        $scope.clear = function() {

        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setGoDoc = function ($file, hrEmpPromotionInfo)
        {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e)
                {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function()
                    {
                        hrEmpPromotionInfo.goDoc = base64Data;
                        hrEmpPromotionInfo.goDocContentType = $file.type;
                        if (hrEmpPromotionInfo.goDocName == null)
                        {
                            hrEmpPromotionInfo.goDocName = $file.name;
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
            $rootScope.viewerObject.pageTitle = "Employee Promotion Document : "+modelInfo.instituteTo;
            $rootScope.showPreviewModal();
        };
}]);
