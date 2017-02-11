'use strict';

angular.module('stepApp').controller('HrEmpIncrementInfoProfileController',
    ['$scope', '$stateParams', '$state', '$q', 'HrEmpIncrementInfo', 'HrEmployeeInfo', 'MiscTypeSetupByCategory', 'HrPayScaleSetup','User','Principal','DateUtils',
        function($scope, $stateParams, $state, $q, HrEmpIncrementInfo, HrEmployeeInfo, MiscTypeSetupByCategory, HrPayScaleSetup, User, Principal, DateUtils) {

        $scope.hrEmpIncrementInfo = {};
        $scope.employeeinfos = HrEmployeeInfo.query({filter: 'hrempincrementinfo-is-null'});
        $q.all([$scope.hrEmpIncrementInfo.$promise, $scope.employeeinfos.$promise]).then(function() {
            if (!$scope.hrEmpIncrementInfo.employeeInfo || !$scope.hrEmpIncrementInfo.employeeInfo.id) {
                return $q.reject();
            }
            return HrEmployeeInfo.get({id : $scope.hrEmpIncrementInfo.employeeInfo.id}).$promise;
        }).then(function(employeeInfo) {
            $scope.employeeinfos.push(employeeInfo);
        });

        $scope.misctypesetups = MiscTypeSetupByCategory.get({cat:'JobCategory',stat:'true'});
        $scope.hrpayscalesetups = HrPayScaleSetup.query();
        $scope.load = function(id) {
            HrEmpIncrementInfo.get({id : id}, function(result) {
                $scope.hrEmpIncrementInfo = result;
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
            HrEmpIncrementInfo.get({id: 'my'}, function (result)
            {
                $scope.hrEmpIncrementInfo = result;
                if($scope.hrEmpIncrementInfo== null)
                {
                    $scope.addMode = true;
                    $scope.hrEmpIncrementInfo = $scope.initiateModel();
                    $scope.loadEmployee();
                }
                else{
                    $scope.hrEmployeeInfo = $scope.hrEmpIncrementInfo.employeeInfo;
                    $scope.hrEmpIncrementInfo.viewMode = true;
                    $scope.hrEmpIncrementInfo.viewModeText = "Edit";
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
                $scope.hrEmpIncrementInfo.viewMode = true;
                $scope.hrEmpIncrementInfo.viewModeText = "Add";

            }, function (response) {
                console.log("error: "+response);
                $scope.hasProfile = false;
                $scope.noEmployeeFound = true;
                $scope.isSaving = false;
                $scope.hrEmpIncrementInfo.viewMode = true;
                $scope.hrEmpIncrementInfo.viewModeText = "Add";
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
                incrementAmount: null,
                incrementDate: null,
                basic: null,
                basicDate: null,
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
            $scope.$emit('stepApp:hrEmpIncrementInfoUpdate', result);
            $scope.isSaving = false;
            $scope.hrEmpIncrementInfo.id=result.id;
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
                HrEmpIncrementInfo.update(modelInfo, onSaveSuccess, onSaveError);
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

                HrEmpIncrementInfo.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
            }
        };

        $scope.clear = function() {
        };
}]);
