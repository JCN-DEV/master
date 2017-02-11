'use strict';

angular.module('stepApp').controller('HrEmpChildrenInfoProfileController',
    ['$scope', '$stateParams', '$state', 'HrEmpChildrenInfo', 'HrEmployeeInfo','Principal','User','DateUtils',
        function($scope, $stateParams, $state, HrEmpChildrenInfo, HrEmployeeInfo, Principal, User, DateUtils) {

        $scope.hrEmpChildrenInfo = {};
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.load = function(id) {
            HrEmpChildrenInfo.get({id : id}, function(result) {
                $scope.hrEmpChildrenInfo = result;
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
            console.log("loadChildProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrEmpChildrenInfo.query({id: 'my'}, function (result)
            {
                //console.log("result: "+JSON.stringify(result));
                $scope.hrEmpChildrenInfoList = result;
                if($scope.hrEmpChildrenInfoList.length < 1)
                {
                    $scope.addMode = true;
                    $scope.hrEmpChildrenInfoList.push($scope.initiateModel());
                    $scope.loadEmployee();
                }
                else
                {
                    $scope.hrEmployeeInfo = $scope.hrEmpChildrenInfoList[0].employeeInfo;
                    angular.forEach($scope.hrEmpChildrenInfoList,function(modelInfo)
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
                    if($scope.hrEmpChildrenInfoList.length > 1)
                    {
                        var indx = $scope.hrEmpChildrenInfoList.indexOf(modelInfo);
                        $scope.hrEmpChildrenInfoList.splice(indx, 1);
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
            $scope.hrEmpChildrenInfoList.push(
                {
                    viewMode:false,
                    viewModeText:'Cancel',
                    childrenName: null,
                    childrenNameBn: null,
                    dateOfBirth: null,
                    gender: null,
                    activeStatus: true,
                    logId:0,
                    logStatus:1,
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
                childrenName: null,
                childrenNameBn: null,
                dateOfBirth: null,
                gender: null,
                activeStatus: true,
                logId:0,
                logStatus:1,
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
            $scope.$emit('stepApp:hrEmpChildrenInfoUpdate', result);
            $scope.isSaving = false;
            $scope.hrEmpChildrenInfoList[$scope.selectedIndex].id=result.id;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
            $scope.viewMode = true;
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
                HrEmpChildrenInfo.update(modelInfo, onSaveSuccess, onSaveError);
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

                HrEmpChildrenInfo.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                $scope.addMode = false;
                modelInfo.isLocked = true;
            }
        };

        $scope.clear = function() {

        };
}]);
