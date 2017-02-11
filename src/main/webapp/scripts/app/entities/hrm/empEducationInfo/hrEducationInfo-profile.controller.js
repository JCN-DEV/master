'use strict';

angular.module('stepApp').controller('HrEducationInfoProfileController',
    ['$rootScope','$sce','$scope', '$stateParams', '$state', 'DataUtils', 'entity', 'HrEducationInfo', 'HrEmployeeInfo', 'MiscTypeSetupByCategory','User','Principal','DateUtils',
        function($rootScope, $sce, $scope, $stateParams, $state, DataUtils, entity, HrEducationInfo, HrEmployeeInfo, MiscTypeSetupByCategory, User, Principal, DateUtils) {

        $scope.hrEducationInfo = entity;
        $scope.hremployeeinfos = HrEmployeeInfo.query();

        $scope.educationBoardNames = MiscTypeSetupByCategory.get({cat:'EducationBoard',stat:'true'});
        $scope.educationLevelNames = MiscTypeSetupByCategory.get({cat:'EducationLevel',stat:'true'});

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

        $scope.viewMode = true;
        $scope.addMode = false;
        $scope.noEmployeeFound = false;

        $scope.loadModel = function()
        {
            console.log("loadEducationProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrEducationInfo.query({id: 'my'}, function (result)
            {
                console.log("result:, len: "+result.length);
                $scope.hrEducationInfoList = result;
                if($scope.hrEducationInfoList.length < 1)
                {
                    $scope.addMode = true;
                    $scope.hrEducationInfoList.push($scope.initiateModel());
                    $scope.loadEmployee();
                }
                else
                {
                    $scope.hrEmployeeInfo = $scope.hrEducationInfoList[0].employeeInfo;
                    angular.forEach($scope.hrEducationInfoList,function(modelInfo)
                    {
                        modelInfo.viewMode = true;
                        modelInfo.viewModeText = "Edit";
                        modelInfo.viewModeIcon = "fa fa-pencil-square";
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
                modelInfo.viewModeIcon = "fa fa-ban";
            }
            else
            {
                modelInfo.viewMode = true;
                if(modelInfo.id==null)
                {
                    if($scope.hrEducationInfoList.length > 1)
                    {
                        var indx = $scope.hrEducationInfoList.indexOf(modelInfo);
                        $scope.hrEducationInfoList.splice(indx, 1);
                    }
                    else
                    {
                        modelInfo.viewModeText = "Add";
                    }
                }
                else
                {
                    modelInfo.viewModeText = "Edit";
                    modelInfo.viewModeIcon = "fa fa-pencil-square";
                }
            }
        };

        $scope.addMore = function(){
            $scope.hrEducationInfoList.push(
                {
                    viewMode:false,
                    viewModeText:'Cancel',
                    examTitle: null,
                    majorSubject: null,
                    certSlNumber: null,
                    sessionYear: null,
                    rollNumber: null,
                    instituteName: null,
                    gpaOrCgpa: null,
                    gpaScale: null,
                    passingYear: null,
                    certificateDoc: null,
                    certificateDocContentType: null,
                    certificateDocName: null,
                    transcriptDoc: null,
                    transcriptDocContentType: null,
                    transcriptDocName: null,
                    activeStatus: true,
                    logId:null,
                    logStatus:null,
                    logComments:null,
                    id: null
                }
            );
        };

        $scope.initiateModel = function()
        {
            return {
                viewMode:true,
                viewModeText:'Add',
                examTitle: null,
                majorSubject: null,
                certSlNumber: null,
                sessionYear: null,
                rollNumber: null,
                instituteName: null,
                gpaOrCgpa: null,
                gpaScale: null,
                passingYear: null,
                certificateDoc: null,
                certificateDocContentType: null,
                certificateDocName: null,
                transcriptDoc: null,
                transcriptDocContentType: null,
                transcriptDocName: null,
                activeStatus: true,
                logId:null,
                logStatus:null,
                logComments:null,
                id: null
            };
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrEducationInfoUpdate', result);
            $scope.isSaving = false;
            $scope.viewMode = true;
            $scope.addMode = false;
            $scope.hrEducationInfoList[$scope.selectedIndex].id=result.id;

        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
            $scope.viewMode = true;
        };

        $scope.updateProfile = function (modelInfo, index)
        {
            console.log("Education Info ");
            $scope.selectedIndex = index;
            modelInfo.updateBy = $scope.loggedInUser.id;
            modelInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            modelInfo.activeStatus = true;
            if (modelInfo.id != null)
            {
                modelInfo.logId = 0;
                modelInfo.logStatus = 0;
                HrEducationInfo.update(modelInfo, onSaveSuccess, onSaveError);
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
                HrEducationInfo.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                $scope.addMode = false;
                modelInfo.isLocked = true;
            }
        };

        $scope.clear = function() {

        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

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
}]);
