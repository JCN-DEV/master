'use strict';

angular.module('stepApp').controller('HrEmpActingDutyInfoProfileController',
    ['$rootScope','$sce','$scope', '$stateParams', '$state', 'DataUtils', 'HrEmpActingDutyInfo', 'HrEmployeeInfo','User','Principal','DateUtils',
        function($rootScope, $sce, $scope, $stateParams, $state, DataUtils, HrEmpActingDutyInfo, HrEmployeeInfo, User, Principal, DateUtils) {

        $scope.hrEmpActingDutyInfo = {};
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.load = function(id) {
            HrEmpActingDutyInfo.get({id : id}, function(result) {
                $scope.hrEmpActingDutyInfo = result;
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
            console.log("loadActingDutyProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrEmpActingDutyInfo.get({id: 'my'}, function (result)
            {
                $scope.hrEmpActingDutyInfo = result;
                if($scope.hrEmpActingDutyInfo== null)
                {
                    $scope.addMode = true;
                    $scope.hrEmpActingDutyInfo = $scope.initiateModel();
                    $scope.loadEmployee();
                }
                else{
                    $scope.hrEmployeeInfo = $scope.hrEmpActingDutyInfo.employeeInfo;
                    $scope.hrEmpActingDutyInfo.viewMode = true;
                    $scope.hrEmpActingDutyInfo.viewModeText = "Edit";
                    $scope.hrEmpActingDutyInfo.isLocked = false;
                    if($scope.hrEmpActingDutyInfo.logStatus==0)
                    {
                        $scope.hrEmpActingDutyInfo.isLocked = true;
                    }
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
                $scope.hrEmpActingDutyInfo.viewMode = true;
                $scope.hrEmpActingDutyInfo.viewModeText = "Add";

            }, function (response) {
                console.log("error: "+response);
                $scope.hasProfile = false;
                $scope.noEmployeeFound = true;
                $scope.isSaving = false;
                $scope.hrEmpActingDutyInfo.viewMode = true;
                $scope.hrEmpActingDutyInfo.viewModeText = "Add";
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
                    modelInfo.viewModeText = "Add";
                else
                    modelInfo.viewModeText = "Edit";
            }
        };

        $scope.initiateModel = function()
        {
            return {
                viewMode:true,
                viewModeText:'Add',
                toInstitution: null,
                designation: null,
                toDepartment: null,
                fromDate: null,
                toDate: null,
                officeOrderNumber: null,
                officeOrderDate: null,
                workOnActingDuty: null,
                comments: null,
                goDate: null,
                goDoc: null,
                goDocContentType: null,
                goDocName: null,
                goNumber: null,
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
            $scope.$emit('stepApp:hrEmpActingDutyInfoUpdate', result);
            $scope.isSaving = false;
            $scope.hrEmpActingDutyInfo.id=result.id;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.updateProfile = function (modelInfo)
        {
            modelInfo.updateBy = $scope.loggedInUser.id;
            modelInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            modelInfo.activeStatus = true;
            if (modelInfo.id != null)
            {
                modelInfo.logId = 0;
                modelInfo.logStatus = 2;
                HrEmpActingDutyInfo.update(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                modelInfo.isLocked = true;
            }
            else
            {
                modelInfo.logId = 0;
                modelInfo.logStatus = 1;
                modelInfo.employeeInfo = $scope.hrEmployeeInfo;
                modelInfo.createBy = $scope.loggedInUser.id;
                modelInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());

                HrEmpActingDutyInfo.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                modelInfo.isLocked = true;
            }
        };

        $scope.clear = function() {

        };

        $scope.previewImage = function (modelInfo)
        {
            var blob = $rootScope.b64toBlob(modelInfo.goDoc, modelInfo.goDocContentType);
            //$rootScope.viewerObject.contentUrl = (window.URL || window.webkitURL).createObjectURL(blob);
            $rootScope.viewerObject.contentUrl = $sce.trustAsResourceUrl((window.URL || window.webkitURL).createObjectURL(blob));
            $rootScope.viewerObject.contentType = modelInfo.goDocContentType;
            $rootScope.viewerObject.pageTitle = "Employee Acting Duty Document - "+modelInfo.toInstitution;
            // call the modal
            $rootScope.showPreviewModal();
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setGoDoc = function ($file, hrEmpActingDutyInfo) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        hrEmpActingDutyInfo.goDoc = base64Data;
                        hrEmpActingDutyInfo.goDocContentType = $file.type;
                        if (hrEmpActingDutyInfo.goDocName == null)
                        {
                            hrEmpActingDutyInfo.goDocName = $file.name;
                        }
                    });
                };
            }
        };
}]);
