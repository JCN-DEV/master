'use strict';

angular.module('stepApp').controller('HrEmpAdvIncrementInfoProfileController',
    ['$scope', '$stateParams', '$state', 'DataUtils', 'HrEmpAdvIncrementInfo', 'HrEmployeeInfo', 'MiscTypeSetupByCategory','User','Principal','DateUtils',
        function($scope, $stateParams, $state, DataUtils, HrEmpAdvIncrementInfo, HrEmployeeInfo, MiscTypeSetupByCategory, User, Principal, DateUtils) {

        $scope.hrEmpAdvIncrementInfo = {};
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.misctypesetups = MiscTypeSetupByCategory.get({cat:'JobCategory',stat:'true'});
        $scope.load = function(id) {
            HrEmpAdvIncrementInfo.get({id : id}, function(result) {
                $scope.hrEmpAdvIncrementInfo = result;
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
            HrEmpAdvIncrementInfo.get({id: 'my'}, function (result)
            {
                $scope.hrEmpAdvIncrementInfo = result;
                if($scope.hrEmpAdvIncrementInfo== null)
                {
                    $scope.addMode = true;
                    $scope.hrEmpAdvIncrementInfo = $scope.initiateModel();
                    $scope.loadEmployee();
                }
                else{
                    $scope.hrEmployeeInfo = $scope.hrEmpAdvIncrementInfo.employeeInfo;
                    $scope.hrEmpAdvIncrementInfo.viewMode = true;
                    $scope.hrEmpAdvIncrementInfo.viewModeText = "Edit";
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
                $scope.hrEmpAdvIncrementInfo.viewMode = true;
                $scope.hrEmpAdvIncrementInfo.viewModeText = "Add";

            }, function (response) {
                console.log("error: "+response);
                $scope.hasProfile = false;
                $scope.noEmployeeFound = true;
                $scope.isSaving = false;
                $scope.hrEmpAdvIncrementInfo.viewMode = true;
                $scope.hrEmpAdvIncrementInfo.viewModeText = "Add";
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
                postName: null,
                purpose: null,
                incrementAmount: null,
                orderDate: null,
                orderNumber: null,
                remarks: null,
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
            $scope.$emit('stepApp:hrEmpAdvIncrementInfoUpdate', result);
            $scope.isSaving = false;
            $scope.hrEmpAdvIncrementInfo.id=result.id;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.updateProfile = function (modelInfo)
        {
            $scope.isSaving = true;
            modelInfo.updateBy = $scope.loggedInUser.id;
            modelInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            modelInfo.activeStatus = true;
            if (modelInfo.id != null)
            {
                modelInfo.logId = 0;
                modelInfo.logStatus = 0;
                HrEmpAdvIncrementInfo.update(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
            }
            else
            {
                modelInfo.logId = 0;
                modelInfo.logStatus = 0;
                modelInfo.employeeInfo = $scope.hrEmployeeInfo;
                modelInfo.createBy = $scope.loggedInUser.id;
                modelInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());

                HrEmpAdvIncrementInfo.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
            }
        };

        $scope.clear = function() {
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setGoDoc = function ($file, hrEmpAdvIncrementInfo) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        hrEmpAdvIncrementInfo.goDoc = base64Data;
                        hrEmpAdvIncrementInfo.goDocContentType = $file.type;
                        if (hrEmpAdvIncrementInfo.goDocName == null)
                        {
                            hrEmpAdvIncrementInfo.goDocName = $file.name;
                        }
                    });
                };
            }
        };
}]);
