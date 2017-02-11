'use strict';

angular.module('stepApp').controller('HrEmpTrainingInfoProfileController',
    ['$rootScope','$sce','$scope', '$stateParams', '$state', 'DataUtils', 'entity', 'HrEmpTrainingInfo', 'HrEmployeeInfo', 'MiscTypeSetupByCategory','User','Principal','DateUtils',
        function($rootScope,$sce, $scope, $stateParams, $state, DataUtils, entity, HrEmpTrainingInfo, HrEmployeeInfo, MiscTypeSetupByCategory, User, Principal, DateUtils) {

        $scope.hrEmpTrainingInfo = entity;
        $scope.trainingTypes   = MiscTypeSetupByCategory.get({cat:'TrainingType',stat:'true'});

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
            console.log("loadTrainingProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrEmpTrainingInfo.query({id: 'my'}, function (result)
            {
                console.log("result:, len: "+result.length);
                $scope.hrEmpTrainingInfoList = result;
                if($scope.hrEmpTrainingInfoList.length < 1)
                {
                    $scope.addMode = true;
                    $scope.hrEmpTrainingInfoList.push($scope.initiateModel());
                    $scope.loadEmployee();
                }
                else
                {
                    $scope.hrEmployeeInfo = $scope.hrEmpTrainingInfoList[0].employeeInfo;
                    angular.forEach($scope.hrEmpTrainingInfoList,function(modelInfo)
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
                    if($scope.hrEmpTrainingInfoList.length > 1)
                    {
                        var indx = $scope.hrEmpTrainingInfoList.indexOf(modelInfo);
                        $scope.hrEmpTrainingInfoList.splice(indx, 1);
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
            $scope.hrEmpTrainingInfoList.push(
                {
                    viewMode:false,
                    viewModeText:'Cancel',
                    instituteName: null,
                    courseTitle: null,
                    durationFrom: null,
                    durationTo: null,
                    result: null,
                    officeOrderNo: null,
                    jobCategory: null,
                    country: null,
                    goOrderDoc: null,
                    goOrderDocContentType: null,
                    goOrderDocName: null,
                    certDoc: null,
                    certDocContentType: null,
                    certDocName: null,
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
                instituteName: null,
                courseTitle: null,
                durationFrom: null,
                durationTo: null,
                result: null,
                officeOrderNo: null,
                jobCategory: null,
                country: null,
                goOrderDoc: null,
                goOrderDocContentType: null,
                goOrderDocName: null,
                certDoc: null,
                certDocContentType: null,
                certDocName: null,
                logId:null,
                logStatus:null,
                logComments:null,
                activeStatus: true,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };

        var onSaveSuccess = function (result)
        {
            $scope.$emit('stepApp:hrEmpTrainingInfoUpdate', result);
            $scope.isSaving = false;
            $scope.viewMode = true;
            $scope.addMode = false;
            $scope.hrEmpTrainingInfoList[$scope.selectedIndex].id=result.id;
        };

        var onSaveError = function (result)
        {
            $scope.isSaving = false;
        };

        $scope.updateProfile = function (modelInfo, index)
        {
            $scope.selectedIndex = index;
            modelInfo.updateBy = $scope.loggedInUser.id;
            modelInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            modelInfo.activeStatus = true;

            if (modelInfo.id != null)
            {
                modelInfo.logId = 0;
                modelInfo.logStatus = 0;
                HrEmpTrainingInfo.update(modelInfo, onSaveSuccess, onSaveError);
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

                HrEmpTrainingInfo.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                $scope.addMode = false;
                modelInfo.isLocked = true;
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
                        if (hrEmpTrainingInfo.goOrderDocName == null|| hrEmpTrainingInfo.goOrderDocName == '')
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
                        if (hrEmpTrainingInfo.certDocName == null|| hrEmpTrainingInfo.certDocName == '')
                        {
                            hrEmpTrainingInfo.certDocName = $file.name;
                        }
                    });
                };
            }
        };
}]);
