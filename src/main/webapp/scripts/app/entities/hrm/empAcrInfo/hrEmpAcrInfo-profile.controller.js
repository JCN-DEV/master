'use strict';

angular.module('stepApp').controller('HrEmpAcrInfoProfileController',
    ['$scope', '$stateParams', '$state', 'HrEmpAcrInfo', 'HrEmployeeInfo','User','Principal','DateUtils',
        function($scope, $stateParams, $state, HrEmpAcrInfo, HrEmployeeInfo, User, Principal, DateUtils) {

        $scope.hrEmpAcrInfo = {};

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
            console.log("loadAwarddProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrEmpAcrInfo.query({id: 'my'}, function (result)
            {
                console.log("result:, len: "+result.length);
                $scope.hrEmpAcrInfoList = result;
                if($scope.hrEmpAcrInfoList.length < 1)
                {
                    $scope.addMode = true;
                    $scope.hrEmpAcrInfoList.push($scope.initiateModel());
                    $scope.loadEmployee();
                }
                else
                {
                    $scope.hrEmployeeInfo = $scope.hrEmpAcrInfoList[0].employeeInfo;
                    angular.forEach($scope.hrEmpAcrInfoList,function(modelInfo)
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
                    if($scope.hrEmpAcrInfoList.length > 1)
                    {
                        var indx = $scope.hrEmpAcrInfoList.indexOf(modelInfo);
                        $scope.hrEmpAcrInfoList.splice(indx, 1);
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
            $scope.hrEmpAcrInfoList.push(
                {
                    viewMode:false,
                    viewModeText:'Cancel',
                    acrYear: null,
                    totalMarks: null,
                    overallEvaluation: null,
                    promotionStatus: null,
                    acrDate: null,
                    logId: null,
                    logStatus: null,
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
                acrYear: null,
                totalMarks: null,
                overallEvaluation: null,
                promotionStatus: null,
                acrDate: null,
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
            $scope.$emit('stepApp:hrEmpAcrInfoUpdate', result);
            $scope.isSaving = false;
            $scope.hrEmpAcrInfoList[$scope.selectedIndex].id=result.id;
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
                HrEmpAcrInfo.update(modelInfo, onSaveSuccess, onSaveError);
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

                HrEmpAcrInfo.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                $scope.addMode = false;
                modelInfo.isLocked = true;
            }
        };

        $scope.clear = function() {

        };
}]);
