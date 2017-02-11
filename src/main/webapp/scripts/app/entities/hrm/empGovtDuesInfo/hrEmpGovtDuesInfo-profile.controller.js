'use strict';

angular.module('stepApp').controller('HrEmpGovtDuesInfoProfileController',
    ['$scope', '$stateParams', '$state', 'HrEmpGovtDuesInfo', 'HrEmployeeInfo','User','Principal','DateUtils',
        function($scope, $stateParams, $state, HrEmpGovtDuesInfo, HrEmployeeInfo, User, Principal, DateUtils) {

        $scope.hrEmpGovtDuesInfo = {};
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.load = function(id) {
            HrEmpGovtDuesInfo.get({id : id}, function(result) {
                $scope.hrEmpGovtDuesInfo = result;
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
            console.log("loadGovtDuesProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrEmpGovtDuesInfo.get({id: 'my'}, function (result)
            {
                $scope.hrEmpGovtDuesInfo = result;
                if($scope.hrEmpGovtDuesInfo== null)
                {
                    $scope.addMode = true;
                    $scope.hrEmpGovtDuesInfo = $scope.initiateModel();
                    $scope.loadEmployee();
                }
                else{
                    $scope.hrEmployeeInfo = $scope.hrEmpGovtDuesInfo.employeeInfo;
                    $scope.hrEmpGovtDuesInfo.viewMode = true;
                    $scope.hrEmpGovtDuesInfo.viewModeText = "Edit";
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
                $scope.hrEmpGovtDuesInfo.viewMode = true;
                $scope.hrEmpGovtDuesInfo.viewModeText = "Add";

            }, function (response) {
                console.log("error: "+response);
                $scope.hasProfile = false;
                $scope.noEmployeeFound = true;
                $scope.isSaving = false;
                $scope.hrEmpGovtDuesInfo.viewMode = true;
                $scope.hrEmpGovtDuesInfo.viewModeText = "Add";
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
                description: null,
                dueAmount: null,
                claimerAuthority: null,
                comments: null,
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

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrEmpGovtDuesInfoUpdate', result);
            $scope.isSaving = false;
            $scope.hrEmpGovtDuesInfo.id=result.id;
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
                HrEmpGovtDuesInfo.update(modelInfo, onSaveSuccess, onSaveError);
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

                HrEmpGovtDuesInfo.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
            }
        };

        $scope.clear = function() {

        };
}]);
