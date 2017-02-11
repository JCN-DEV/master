'use strict';

angular.module('stepApp').controller('HrEmpPublicationInfoProfileController',
    ['$rootScope','$sce','$scope', '$stateParams', '$state', '$modal', 'DataUtils', 'HrEmpPublicationInfo', 'HrEmployeeInfo','User','Principal','DateUtils','MiscTypeSetupByCategory',
        function($rootScope, $sce, $scope, $stateParams, $state, $modal, DataUtils, HrEmpPublicationInfo, HrEmployeeInfo, User, Principal, DateUtils,MiscTypeSetupByCategory) {

        $scope.hrEmpPublicationInfo = {};
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.publicationTypeList  = MiscTypeSetupByCategory.get({cat:'PublicationType',stat:'true'});
        $scope.load = function(id) {
            HrEmpPublicationInfo.get({id : id}, function(result) {
                $scope.hrEmpPublicationInfo = result;
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

        $scope.viewMode = true;
        $scope.addMode = false;
        $scope.noEmployeeFound = false;

        $scope.loadModel = function()
        {
            console.log("loadPublicationProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrEmpPublicationInfo.query({id: 'my'}, function (result)
            {
                console.log("result:, len: "+result.length);
                $scope.hrEmpPublicationInfoList = result;
                if($scope.hrEmpPublicationInfoList.length < 1)
                {
                    $scope.addMode = true;
                    $scope.hrEmpPublicationInfoList.push($scope.initiateModel());
                    $scope.loadEmployee();
                }
                else
                {
                    $scope.hrEmployeeInfo = $scope.hrEmpPublicationInfoList[0].employeeInfo;
                    angular.forEach($scope.hrEmpPublicationInfoList,function(modelInfo)
                    {
                        modelInfo.viewMode = true;
                        modelInfo.viewModeText = "Edit";
                        modelInfo.isLocked = false;
                        if(modelInfo.logStatus==0)
                        {
                            modelInfo.isLocked = true;
                        }
                    });
                }
            }, function (response)
            {
                console.log("error: "+response);
                $scope.hasProfile = false;
                $scope.addMode = true;
                $scope.loadEmployee();
            })
        };

        $scope.loadEmployee = function ()
        {
            console.log("loadEmployeeProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrEmployeeInfo.get({id: 'my'}, function (result) {
                $scope.hrEmployeeInfo = result;

            }, function (response) {
                console.log("error: "+response);
                $scope.hasProfile = false;
                $scope.noEmployeeFound = true;
                $scope.isSaving = false;
            })
        };
        $scope.loadModel();

        $scope.changeProfileMode = function (modelInfo)
        {
            if(modelInfo.viewMode)
            {
                modelInfo.viewMode = false;
                modelInfo.viewModeText = "Cancel";
            }
            else
            {
                modelInfo.viewMode = true;
                if(modelInfo.id==null)
                {
                    if($scope.hrEmpPublicationInfoList.length > 1)
                    {
                        var indx = $scope.hrEmpPublicationInfoList.indexOf(modelInfo);
                        $scope.hrEmpPublicationInfoList.splice(indx, 1);
                    }
                    else
                    {
                        modelInfo.viewModeText = "Add";
                    }
                }
                else
                    modelInfo.viewModeText = "Edit";
            }
        };

        $scope.addMore = function()
        {
            $scope.hrEmpPublicationInfoList.push(
                {
                    viewMode:false,
                    viewModeText:'Cancel',
                    publicationTitle: null,
                    publicationDate: null,
                    remarks: null,
                    publicationLink: null,
                    publicationDoc: null,
                    publicationDocContentType: null,
                    publicationDocName: null,
                    logId:null,
                    logStatus:null,
                    logComments:null,
                    activeStatus: true,
                    createDate: null,
                    createBy: null,
                    updateDate: null,
                    updateBy: null,
                    id: null
                }
            );
        };

        $scope.initiateModel = function()
        {
            return {
                viewMode:true,
                viewModeText:'Add',
                publicationTitle: null,
                publicationDate: null,
                remarks: null,
                publicationLink: null,
                publicationDoc: null,
                publicationDocContentType: null,
                publicationDocName: null,
                logId: null,
                logStatus: null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };

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
            $scope.hrEmpPublicationInfoList[$scope.selectedIndex].id=result.id;
            //console.log("result: "+JSON.stringify($scope.hrEmpPublicationInfoList[$scope.selectedIndex]));

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.updateProfile = function (modelInfo, index)
        {
            console.log("selectedIndex: "+index);
            $scope.selectedIndex = index;
            modelInfo.updateBy = $scope.loggedInUser.id;
            modelInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            modelInfo.activeStatus = true;

            if (modelInfo.id != null)
            {
                modelInfo.logId = 0;
                modelInfo.logStatus = 0;
                HrEmpPublicationInfo.update(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                modelInfo.isLocked = true;
            }
            else
            {
                modelInfo.logId = 0;
                modelInfo.logStatus = 0;
                modelInfo.employeeInfo = $scope.hrEmployeeInfo;
                modelInfo.createBy = $scope.loggedInUser.id;
                modelInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());

                HrEmpPublicationInfo.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                $scope.addMode = false;
                modelInfo.isLocked = true;
            }
        };

        $scope.previewImage = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.publicationDoc, modelInfo.publicationDocContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            //$rootScope.viewerObject.contentUrl = (window.URL || window.webkitURL).createObjectURL(blob);
            $rootScope.viewerObject.contentType = modelInfo.publicationDocContentType;
            $rootScope.viewerObject.pageTitle = "Employee Publication Document";
            // call the modal
            $rootScope.showPreviewModal();
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
                    $scope.$apply(function()
                    {
                        hrEmpPublicationInfo.publicationDoc = base64Data;
                        hrEmpPublicationInfo.publicationDocContentType = $file.type;
                        if (hrEmpPublicationInfo.publicationDocName == null)
                        {
                            hrEmpPublicationInfo.publicationDocName = $file.name;
                        }
                        var blob = $rootScope.b64toBlob(hrEmpPublicationInfo.publicationDoc , hrEmpPublicationInfo.publicationDocContentType);
                        hrEmpPublicationInfo.publFileContentUrl = (window.URL || window.webkitURL).createObjectURL(blob);
                    });
                };
            }
        };
}]);
