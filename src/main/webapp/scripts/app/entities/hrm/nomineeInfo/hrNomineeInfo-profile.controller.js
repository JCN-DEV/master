'use strict';

angular.module('stepApp').controller('HrNomineeInfoProfileController',
    ['$scope', '$stateParams', '$state', 'HrNomineeInfo', 'HrEmployeeInfo','Principal','User','DateUtils','MiscTypeSetupByCategory',
        function($scope, $stateParams, $state, HrNomineeInfo, HrEmployeeInfo, Principal, User, DateUtils, MiscTypeSetupByCategory) {

        $scope.hrNomineeInfo = {};
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.nomineeRelationships = MiscTypeSetupByCategory.get({cat:'NomineeRelationship'});
        $scope.load = function(id) {
            HrNomineeInfo.get({id : id}, function(result) {
                $scope.hrNomineeInfo = result;
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
            console.log("loadNomineeProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrNomineeInfo.query({id: 'my'}, function (result)
            {
                //console.log("result:, len: "+JSON.stringify(result));
                $scope.hrNomineeInfoList = result;
                if($scope.hrNomineeInfoList.length < 1)
                {
                    $scope.addMode = true;
                    $scope.hrNomineeInfoList.push($scope.initiateModel());
                    $scope.loadEmployee();
                }
                else
                {
                    $scope.hrEmployeeInfo = $scope.hrNomineeInfoList[0].employeeInfo;
                    angular.forEach($scope.hrNomineeInfoList,function(modelInfo)
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
                $scope.hrNomineeInfo.viewMode = true;
                $scope.hrNomineeInfo.viewModeText = "Add";

            }, function (response) {
                console.log("error: "+response);
                $scope.hasProfile = false;
                $scope.noEmployeeFound = true;
                $scope.isSaving = false;
                $scope.hrNomineeInfo.viewMode = true;
                $scope.hrNomineeInfo.viewModeText = "Add";
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
                    if($scope.hrNomineeInfoList.length > 1)
                    {
                        var indx = $scope.hrNomineeInfoList.indexOf(modelInfo);
                        $scope.hrNomineeInfoList.splice(indx, 1);
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
            $scope.hrNomineeInfoList.push(
                {
                    viewMode:false,
                    viewModeText:'Cancel',
                    nomineeName: null,
                    nomineeNameBn: null,
                    birthDate: null,
                    gender: null,
                    occupation: null,
                    designation: null,
                    nationalId: null,
                    mobileNumber: null,
                    address: null,
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
                nomineeName: null,
                nomineeNameBn: null,
                birthDate: null,
                gender: null,
                occupation: null,
                designation: null,
                nationalId: null,
                mobileNumber: null,
                address: null,
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
            $scope.$emit('stepApp:hrNomineeInfoUpdate', result);
            $scope.isSaving = false;
            $scope.hrNomineeInfoList[$scope.selectedIndex].id=result.id;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.updateProfile = function (modelInfo, index)
        {
            $scope.selectedIndex = index;
            $scope.isSaving = true;
            modelInfo.updateBy = $scope.loggedInUser.id;
            modelInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            modelInfo.activeStatus = true;

            if (modelInfo.id != null)
            {
                modelInfo.logId = 0;
                modelInfo.logStatus = 0;
                HrNomineeInfo.update(modelInfo, onSaveSuccess, onSaveError);
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

                HrNomineeInfo.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                $scope.addMode = false;
                modelInfo.isLocked = true;
            }
        };

        $scope.clear = function() {

        };
}]);
