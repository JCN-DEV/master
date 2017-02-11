'use strict';

angular.module('stepApp').controller('HrEmpServiceHistoryProfileController',
    ['$rootScope','$sce','$scope', '$stateParams', '$state', 'DataUtils', 'HrEmpServiceHistory', 'HrEmployeeInfo','User','Principal','DateUtils',
        function($rootScope, $sce, $scope, $stateParams, $state, DataUtils, HrEmpServiceHistory, HrEmployeeInfo, User, Principal, DateUtils)
        {

        $scope.hrEmpServiceHistory = {};
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.load = function(id) {
            HrEmpServiceHistory.get({id : id}, function(result) {
                $scope.hrEmpServiceHistory = result;
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
            console.log("loadHistoryProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrEmpServiceHistory.query({id: 'my'}, function (result)
            {
                console.log("result:, len: "+result.length);
                $scope.hrEmpServiceHistoryList = result;
                if($scope.hrEmpServiceHistoryList.length < 1)
                {
                    $scope.addMode = true;
                    $scope.hrEmpServiceHistoryList.push($scope.initiateModel());
                    $scope.loadEmployee();
                }
                else
                {
                    $scope.hrEmployeeInfo = $scope.hrEmpServiceHistoryList[0].employeeInfo;
                    angular.forEach($scope.hrEmpServiceHistoryList,function(modelInfo)
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
                    if($scope.hrEmpServiceHistoryList.length > 1)
                    {
                        var indx = $scope.hrEmpServiceHistoryList.indexOf(modelInfo);
                        $scope.hrEmpServiceHistoryList.splice(indx, 1);
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
            $scope.hrEmpServiceHistoryList.push(
                {
                    viewMode:false,
                    viewModeText:'Cancel',
                    serviceDate: null,
                    gazettedDate: null,
                    encadrementDate: null,
                    nationalSeniority: null,
                    cadreNumber: null,
                    goDate: null,
                    goDoc: null,
                    goDocContentType: null,
                    goDocName: null,
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
                serviceDate: null,
                gazettedDate: null,
                encadrementDate: null,
                nationalSeniority: null,
                cadreNumber: null,
                goDate: null,
                goDoc: null,
                goDocContentType: null,
                goDocName: null,
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
            $scope.hrEmpServiceHistoryList[$scope.selectedIndex].id=result.id;
        };

        var onSaveError = function (result) {
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
                HrEmpServiceHistory.update(modelInfo, onSaveSuccess, onSaveError);
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

                HrEmpServiceHistory.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                $scope.addMode = false;
                modelInfo.isLocked = true;
            }
        };

        $scope.previewImage = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.goDoc, modelInfo.goDocContentType);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            //$rootScope.viewerObject.contentUrl = (window.URL || window.webkitURL).createObjectURL(blob);
            $rootScope.viewerObject.contentType = modelInfo.goDocContentType;
            $rootScope.viewerObject.pageTitle = "Employee Service History Document";
            $rootScope.showPreviewModal();
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
}]);
