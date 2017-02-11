'use strict';

angular.module('stepApp').controller('HrEmpTrainingInfoDialogController',
    ['$scope','$rootScope', '$sce', '$stateParams', '$state', 'DataUtils', 'entity', 'HrEmpTrainingInfo', 'HrEmployeeInfoByWorkArea', 'MiscTypeSetupByCategory','User','Principal','DateUtils',
        function($scope,$rootScope, $sce, $stateParams, $state, DataUtils, entity, HrEmpTrainingInfo, HrEmployeeInfoByWorkArea, MiscTypeSetupByCategory, User, Principal, DateUtils) {

        $scope.hrEmpTrainingInfo = entity;
        $scope.trainingTypes   = MiscTypeSetupByCategory.get({cat:'TrainingType',stat:'true'});
        $scope.load = function(id)
        {
            HrEmpTrainingInfo.get({id : id}, function(result) {
                $scope.hrEmpTrainingInfo = result;
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

        var onSaveSuccess = function (result)
        {
            $scope.$emit('stepApp:hrEmpTrainingInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrEmpTrainingInfo');
            //console.log("TrainingSave: "+JSON.stringify(result));
        };

        var onSaveError = function (result)
        {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmpTrainingInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpTrainingInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            $scope.hrEmpTrainingInfo.logId = 0;
            $scope.hrEmpTrainingInfo.logStatus = 6;
            if ($scope.hrEmpTrainingInfo.id != null)
            {
                HrEmpTrainingInfo.update($scope.hrEmpTrainingInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEmpTrainingInfo.updated');
            }
            else
            {
                $scope.hrEmpTrainingInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEmpTrainingInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmpTrainingInfo.save($scope.hrEmpTrainingInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpTrainingInfo.created');
            }
        };

        $scope.clear = function()
        {

        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

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

        $scope.setGoOrderDoc = function ($file, hrEmpTrainingInfo)
        {
            if ($file)
            {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        hrEmpTrainingInfo.goOrderDoc = base64Data;
                        hrEmpTrainingInfo.goOrderDocContentType = $file.type;
                        if (hrEmpTrainingInfo.goOrderDocName == null)
                        {
                            hrEmpTrainingInfo.goOrderDocName = $file.name;
                        }
                    });
                };
            }
        };

        $scope.setCertDoc = function ($file, hrEmpTrainingInfo)
        {
            if ($file)
            {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        hrEmpTrainingInfo.certDoc = base64Data;
                        hrEmpTrainingInfo.certDocContentType = $file.type;
                        if (hrEmpTrainingInfo.certDocName == null)
                        {
                            hrEmpTrainingInfo.certDocName = $file.name;
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
            $rootScope.viewerObject.pageTitle = "Employee Training GO Order Document";
            $rootScope.showPreviewModal();
        };

        $scope.previewCertDoc = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.certDoc, modelInfo.certDocContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.certDocContentType;
            $rootScope.viewerObject.pageTitle = "Employee Training Certificate Document";
            $rootScope.showPreviewModal();
        };
}]);
