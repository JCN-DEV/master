'use strict';

angular.module('stepApp').controller('HrEmpServiceHistoryDialogController',
    ['$scope', '$rootScope', '$sce', '$stateParams', '$state', 'DataUtils', 'entity', 'HrEmpServiceHistory', 'HrEmployeeInfoByWorkArea','User','Principal','DateUtils','MiscTypeSetupByCategory',
        function($scope, $rootScope, $sce, $stateParams, $state, DataUtils, entity, HrEmpServiceHistory, HrEmployeeInfoByWorkArea, User, Principal, DateUtils, MiscTypeSetupByCategory)
        {

        $scope.hrEmpServiceHistory = entity;
        $scope.load = function(id) {
            HrEmpServiceHistory.get({id : id}, function(result) {
                $scope.hrEmpServiceHistory = result;
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
            $scope.$emit('stepApp:hrEmpServiceHistoryUpdate', result);
            $scope.isSaving = false;
            $state.go('hrEmpServiceHistory');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmpServiceHistory.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpServiceHistory.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrEmpServiceHistory.id != null)
            {
                $scope.hrEmpServiceHistory.logId = 0;
                $scope.hrEmpServiceHistory.logStatus = 6;
                HrEmpServiceHistory.update($scope.hrEmpServiceHistory, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEmpServiceHistory.updated');
            }
            else
            {
                $scope.hrEmpServiceHistory.logId = 0;
                $scope.hrEmpServiceHistory.logStatus = 6;
                $scope.hrEmpServiceHistory.createBy = $scope.loggedInUser.id;
                $scope.hrEmpServiceHistory.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmpServiceHistory.save($scope.hrEmpServiceHistory, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpServiceHistory.created');
            }
        };

        $scope.clear = function() {

        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setGoDoc = function ($file, hrEmpServiceHistory)
        {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        hrEmpServiceHistory.goDoc = base64Data;
                        hrEmpServiceHistory.goDocContentType = $file.type;
                        if (hrEmpServiceHistory.goDocName == null)
                        {
                            hrEmpServiceHistory.goDocName = $file.name;
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
            $rootScope.viewerObject.pageTitle = "Employee Service History Document";
            $rootScope.showPreviewModal();
        };
}]);
