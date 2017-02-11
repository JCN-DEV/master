'use strict';

angular.module('stepApp').controller('HrEmpPublicationInfoDialogController',
    ['$rootScope','$scope', '$sce', '$stateParams', '$state', 'DataUtils', 'entity', 'HrEmpPublicationInfo', 'HrEmployeeInfoByWorkArea','User','Principal','DateUtils','MiscTypeSetupByCategory',
        function($rootScope,  $scope, $sce, $stateParams, $state, DataUtils, entity, HrEmpPublicationInfo, HrEmployeeInfoByWorkArea, User, Principal, DateUtils,MiscTypeSetupByCategory) {

        $scope.hrEmpPublicationInfo = entity;
        $scope.load = function(id) {
            HrEmpPublicationInfo.get({id : id}, function(result) {
                $scope.hrEmpPublicationInfo = result;
            });
        };

        $scope.hremployeeinfos  = [];
        $scope.workAreaList         = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});
        $scope.publicationTypeList  = MiscTypeSetupByCategory.get({cat:'PublicationType',stat:'true'});

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
            $scope.$emit('stepApp:hrEmpPublicationInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrEmpPublicationInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmpPublicationInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpPublicationInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());

            if ($scope.hrEmpPublicationInfo.id != null)
            {
                $scope.hrEmpPublicationInfo.logId = 0;
                $scope.hrEmpPublicationInfo.logStatus = 6;
                HrEmpPublicationInfo.update($scope.hrEmpPublicationInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEmpPublicationInfo.updated');
            }
            else
            {
                $scope.hrEmpPublicationInfo.logId = 0;
                $scope.hrEmpPublicationInfo.logStatus = 6;
                $scope.hrEmpPublicationInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEmpPublicationInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmpPublicationInfo.save($scope.hrEmpPublicationInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpPublicationInfo.created');
            }
        };

        $scope.clear = function() {

        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setPublicationDoc = function ($file, hrEmpPublicationInfo) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        hrEmpPublicationInfo.publicationDoc = base64Data;
                        hrEmpPublicationInfo.publicationDocContentType = $file.type;
                        if (hrEmpPublicationInfo.publicationDocName == null)
                        {
                            hrEmpPublicationInfo.publicationDocName = $file.name;
                        }
                    });
                };
            }
        };

        $scope.previewImage = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.publicationDoc, modelInfo.publicationDocContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.publicationDocContentType;
            $rootScope.viewerObject.pageTitle = "Employee Publication Document";
            $rootScope.showPreviewModal();
        };
    }]);
