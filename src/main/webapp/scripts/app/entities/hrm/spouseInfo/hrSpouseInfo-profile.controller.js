'use strict';

angular.module('stepApp').controller('HrSpouseInfoProfileController',
    ['$scope', '$stateParams', '$state', 'HrSpouseInfo', 'HrEmployeeInfo','Principal','User','DateUtils',
        function($scope, $stateParams, $state, HrSpouseInfo, HrEmployeeInfo, Principal, User, DateUtils) {

        $scope.hrSpouseInfo = {};
        $scope.hremployeeinfos = HrEmployeeInfo.query();

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
            console.log("loadSpouseProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrSpouseInfo.query({id: 'my'}, function (result)
            {
                //console.log("result:, len: "+JSON.stringify(result));

                if(result.length < 1)
                {
                    //console.log("spouse is null");
                    $scope.addMode = true;
                    $scope.hrSpouseInfo = $scope.initiateModel();
                    $scope.hrSpouseInfo.viewMode = true;
                    $scope.hrSpouseInfo.viewModeText = "Edit";
                    console.log("InitSpouse:, len: "+JSON.stringify($scope.hrSpouseInfo));
                    $scope.loadEmployee();
                }
                else
                {
                    $scope.hrSpouseInfo = result[0];
                    $scope.hrEmployeeInfo = $scope.hrSpouseInfo.employeeInfo;

                    //console.log("spouse is not null"+JSON.stringify($scope.hrSpouseInfo));
                    $scope.hrEmployeeInfo = $scope.hrSpouseInfo.employeeInfo;
                    $scope.hrSpouseInfo.viewMode = true;
                    $scope.hrSpouseInfo.viewModeText = "Edit";
                    $scope.hrSpouseInfo.isLocked = false;
                    if($scope.hrSpouseInfo.logStatus==0)
                    {
                        $scope.hrSpouseInfo.isLocked = true;
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
                $scope.hrSpouseInfo.viewMode = true;
                $scope.hrSpouseInfo.viewModeText = "Add";

            }, function (response) {
                console.log("error: "+response);
                $scope.hasProfile = false;
                $scope.noEmployeeFound = true;
                $scope.isSaving = false;
                $scope.hrSpouseInfo.viewMode = true;
                $scope.hrSpouseInfo.viewModeText = "Add";
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
                spouseName: null,
                spouseNameBn: null,
                birthDate: null,
                gender: null,
                relationship: null,
                isNominee: null,
                occupation: null,
                organization: null,
                designation: null,
                nationalId: null,
                emailAddress: null,
                contactNumber: null,
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
            $scope.$emit('stepApp:hrSpouseInfoUpdate', result);
            $scope.isSaving = false;
            $scope.hrSpouseInfo.id=result.id;
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

            //console.log("model: "+JSON.stringify(modelInfo));
            if (modelInfo.id != null)
            {
                modelInfo.logId = 0;
                modelInfo.logStatus = 0;
                HrSpouseInfo.update(modelInfo, onSaveSuccess, onSaveError);
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

                HrSpouseInfo.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                $scope.addMode = false;
                modelInfo.isLocked = true;
            }
        };

        $scope.clear = function() {
        };
}]);
