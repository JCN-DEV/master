'use strict';

angular.module('stepApp').controller('PrlBudgetSetupInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$timeout', '$state', 'entity', 'PrlBudgetSetupInfo', 'PrlEconomicalCodeInfo', 'PrlAllowDeductInfo','User','Principal','DateUtils','PrlBudgetSetupInfoByFilter',
        function($scope, $rootScope, $stateParams, $timeout, $state, entity, PrlBudgetSetupInfo, PrlEconomicalCodeInfo, PrlAllowDeductInfo, User, Principal, DateUtils,PrlBudgetSetupInfoByFilter) {

        $scope.prlBudgetSetupInfo = entity;
        $scope.prleconomicalcodeinfos = PrlEconomicalCodeInfo.query();
        $scope.prlallowdeductinfos = PrlAllowDeductInfo.query();
        $scope.load = function(id) {
            PrlBudgetSetupInfo.get({id : id}, function(result) {
                $scope.prlBudgetSetupInfo = result;
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

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:prlBudgetSetupInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('prlBudgetSetupInfo');
        };
        $scope.saveCounter = 0;
        $scope.saveCounterActual = 0;
        $scope.saveCounterTotal = 0;
        var onSaveBudgetSuccess = function (result) {
            $scope.saveCounter++;
            console.log("Success: counter: "+$scope.saveCounter+", actual: "+$scope.saveCounterActual);

            if($scope.saveCounter == $scope.saveCounterActual)
            {
                $scope.loadAllBudgetList();
                $scope.updatMessage = "Data Updated Successfully!!!";
            }
        };
        var onSaveBudgetError = function (result) {
            $scope.saveCounter++;
            console.log("Failed: counter: "+$scope.saveCounter+", actual: "+$scope.saveCounterActual);

            if($scope.saveCounter == $scope.saveCounterActual)
            {
                $scope.loadAllBudgetList();
                $scope.updatMessage = "Data Updated Successfully!!!";
            }
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            $scope.prlBudgetSetupInfo.updateBy = $scope.loggedInUser.id;
            $scope.prlBudgetSetupInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.prlBudgetSetupInfo.id != null) {
                PrlBudgetSetupInfo.update($scope.prlBudgetSetupInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.prlBudgetSetupInfo.updated');
            } else {
                $scope.prlBudgetSetupInfo.createBy = $scope.loggedInUser.id;
                $scope.prlBudgetSetupInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PrlBudgetSetupInfo.save($scope.prlBudgetSetupInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.prlBudgetSetupInfo.created');
            }
        };

        $scope.saveAllBudgetSetup = function ()
        {
            console.log("Saving budget list");
            if($scope.prlBudgetSetupInfo.budgetType!=null &&
                $scope.prlBudgetSetupInfo.budgetYear != null)
            {
                $scope.saveCounter = 0;
                $scope.saveCounterActual = 0;
                $scope.updatMessage = "";
                angular.forEach($scope.prlBudgetSetupInfoList,function(budgetInfo)
                {
                    console.log("Saving budget info name: "+budgetInfo.codeValue);
                    if(budgetInfo.codeValue != null)
                    {
                        $scope.saveCounterActual++;
                        budgetInfo.budgetType = $scope.prlBudgetSetupInfo.budgetType;
                        budgetInfo.budgetYear = $scope.prlBudgetSetupInfo.budgetYear;
                        budgetInfo.updateBy = $scope.loggedInUser.id;
                        budgetInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                        console.log(""+JSON.stringify(budgetInfo));
                        if (budgetInfo.id != null)
                        {
                            PrlBudgetSetupInfo.update(budgetInfo, onSaveBudgetSuccess, onSaveBudgetError);
                            $rootScope.setWarningMessage('stepApp.prlBudgetSetupInfo.updated');
                        }
                        else {
                            budgetInfo.createBy = $scope.loggedInUser.id;
                            budgetInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                            PrlBudgetSetupInfo.save(budgetInfo, onSaveBudgetSuccess, onSaveBudgetError);
                            $rootScope.setSuccessMessage('stepApp.prlBudgetSetupInfo.created');
                        }
                    }
                    else
                    {
                        console.log("Save ignoring");
                    }
                });
            }
        };

        $scope.loadAllBudgetList = function()
        {
            console.log("type: "+$scope.prlBudgetSetupInfo.budgetType+", year:"+$scope.prlBudgetSetupInfo.budgetYear);
            if($scope.prlBudgetSetupInfo.budgetType!=null &&
                $scope.prlBudgetSetupInfo.budgetYear != null)
            {
                PrlBudgetSetupInfoByFilter.query({type: $scope.prlBudgetSetupInfo.budgetType, year:$scope.prlBudgetSetupInfo.budgetYear}, function (result)
                {
                    console.log("loadAllBudgetList2: "+result.length);
                    $scope.prlBudgetSetupInfoList = result;
                    $scope.saveCounterTotal = result.length;
                }, function (response)
                {
                    console.log("error: "+response);
                });
            }
            console.log("loadAllBudgetList");
        };

        $scope.clear = function() {

        };
}]);
