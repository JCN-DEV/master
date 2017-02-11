'use strict';

angular.module('stepApp').controller('HrEmpBankAccountInfoProfileController',
    ['$scope', '$stateParams', 'HrEmpBankAccountInfo', 'HrEmployeeInfo', 'MiscTypeSetupByCategory','User','Principal','DateUtils','HrEmpBankSalaryAccountInfo',
        function($scope, $stateParams, HrEmpBankAccountInfo, HrEmployeeInfo, MiscTypeSetupByCategory, User, Principal, DateUtils,HrEmpBankSalaryAccountInfo) {

        $scope.hrEmpBankAccountInfo = {};
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.misctypesetups = MiscTypeSetupByCategory.get({cat:'BankName',stat:'true'});
        $scope.load = function(id) {
            HrEmpBankAccountInfo.get({id : id}, function(result) {
                $scope.hrEmpBankAccountInfo = result;
            });
        };

        $scope.salaryAccountCounter = 0;
        $scope.loadSalaryAccount= function ()
        {
            HrEmpBankSalaryAccountInfo.query({emplId : $scope.hrEmployeeInfo.id}, function(result)
            {
                $scope.salaryAccountCounter = result.value;
                //console.log(JSON.stringify(result)+", count: "+$scope.salaryAccountCounter);
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
            console.log("loadBankAccountProfile addMode: "+$scope.addMode+", viewMode: "+$scope.viewMode);
            HrEmpBankAccountInfo.query({id: 'my'}, function (result)
            {
                console.log("result:, len: "+result.length);
                $scope.hrEmpBankAccountInfoList = result;
                if($scope.hrEmpBankAccountInfoList.length < 1)
                {
                    $scope.addMode = true;
                    $scope.hrEmpBankAccountInfoList.push($scope.initiateModel());
                    $scope.loadEmployee();
                }
                else
                {
                    $scope.hrEmployeeInfo = $scope.hrEmpBankAccountInfoList[0].employeeInfo;
                    angular.forEach($scope.hrEmpBankAccountInfoList,function(modelInfo)
                    {
                        modelInfo.viewMode = true;
                        modelInfo.viewModeText = "Edit";
                        modelInfo.isLocked = false;
                        if(modelInfo.logStatus==0)
                        {
                            modelInfo.isLocked = true;
                        }
                        if(modelInfo.salaryAccount == true)
                        {
                            $scope.salaryAccountCounter++;
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
                    if($scope.hrEmpBankAccountInfoList.length > 1)
                    {
                        var indx = $scope.hrEmpBankAccountInfoList.indexOf(modelInfo);
                        $scope.hrEmpBankAccountInfoList.splice(indx, 1);
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
            $scope.hrEmpBankAccountInfoList.push(
                {
                    viewMode:false,
                    viewModeText:'Cancel',
                    accountName: null,
                    accountNumber: null,
                    branchName: null,
                    description: null,
                    salaryAccount: false,
                    activeStatus: true,
                    logId: null,
                    logStatus: null,
                    logComments: null,
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
                accountName: null,
                accountNumber: null,
                branchName: null,
                description: null,
                salaryAccount: false,
                activeStatus: true,
                logId: null,
                logStatus: null,
                logComments: null,
                createDate: null,
                createBy: null,
                updateDate: null,
                updateBy: null,
                id: null
            };
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrEmpBankAccountInfoUpdate', result);
            $scope.isSaving = false;
            $scope.hrEmpBankAccountInfoList[$scope.selectedIndex].id=result.id;
            $scope.loadSalaryAccount();
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
                HrEmpBankAccountInfo.update(modelInfo, onSaveSuccess, onSaveError);
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

                HrEmpBankAccountInfo.save(modelInfo, onSaveSuccess, onSaveError);
                modelInfo.viewMode = true;
                modelInfo.viewModeText = "Edit";
                $scope.addMode = false;
                modelInfo.isLocked = true;
            }
        };

        $scope.clear = function() {

        };
}]);
