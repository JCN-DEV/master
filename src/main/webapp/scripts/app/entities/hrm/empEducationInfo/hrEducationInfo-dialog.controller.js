'use strict';

    angular.module('stepApp').controller('HrEducationInfoDialogController',
    ['$scope', '$rootScope','$sce', '$stateParams', '$state', 'DataUtils', 'entity', 'HrEducationInfo', 'HrEmployeeInfoByWorkArea', 'MiscTypeSetupByCategory','User','Principal','DateUtils',
        function($scope, $rootScope, $sce, $stateParams, $state, DataUtils, entity, HrEducationInfo, HrEmployeeInfoByWorkArea, MiscTypeSetupByCategory, User, Principal, DateUtils) {

        $scope.hrEducationInfo = entity;
        $scope.hremployeeinfos  = [];
        $scope.workAreaList     = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});
        $scope.educationBoardNames = MiscTypeSetupByCategory.get({cat:'EducationBoard',stat:'true'});
        $scope.educationLevelNames = MiscTypeSetupByCategory.get({cat:'EducationLevel',stat:'true'});

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
                    //console.log("loggedInUser: "+JSON.stringify($scope.loggedInUser));
                });
            });
        };

        $scope.getLoggedInUser();

        $scope.load = function(id) {
            HrEducationInfo.get({id : id}, function(result) {
                $scope.hrEducationInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrEducationInfoUpdate', result);
            $state.go('hrEducationInfo');
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEducationInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEducationInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrEducationInfo.id != null)
            {
                $scope.hrEducationInfo.logId = 0;
                $scope.hrEducationInfo.logStatus = 6;
                HrEducationInfo.update($scope.hrEducationInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEducationInfo.updated');
            }
            else
            {
                $scope.hrEducationInfo.logId = 0;
                $scope.hrEducationInfo.logStatus = 6;
                $scope.hrEducationInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEducationInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEducationInfo.save($scope.hrEducationInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEducationInfo.created');
            }
        };

        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setCertificateDoc = function ($file, hrEducationInfo) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        hrEducationInfo.certificateDoc = base64Data;
                        hrEducationInfo.certificateDocContentType = $file.type;
                        if (hrEducationInfo.id == null)
                        {
                            hrEducationInfo.certificateDocName = $file.name;
                        }
                    });
                };
            }
        };

        $scope.setTranscriptDoc = function ($file, hrEducationInfo) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        hrEducationInfo.transcriptDoc = base64Data;
                        hrEducationInfo.transcriptDocContentType = $file.type;
                        if (hrEducationInfo.id == null)
                        {
                            hrEducationInfo.transcriptDocName = $file.name;
                        }
                    });
                };
            }
        };

        $scope.previewTransDoc = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.transcriptDoc, modelInfo.transcriptDocContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.transcriptDocContentType;
            $rootScope.viewerObject.pageTitle = "Employee Education Transcript Order Document";
            $rootScope.showPreviewModal();
        };

        $scope.previewCertDoc = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.certificateDoc, modelInfo.certificateDocContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.certificateDocContentType;
            $rootScope.viewerObject.pageTitle = "Employee Education Certificate Document";
            $rootScope.showPreviewModal();
        };
}]);
