'use strict';

angular.module('stepApp').controller('PrlEconomicalCodeInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', '$timeout', 'entity', 'PrlEconomicalCodeInfo', 'PrlAllowDeductInfo','User','Principal','DateUtils','PrlEconomicalCodeInfoByFilter',
        function($scope, $rootScope, $stateParams, $state, $timeout, entity, PrlEconomicalCodeInfo, PrlAllowDeductInfo, User, Principal, DateUtils, PrlEconomicalCodeInfoByFilter) {

        $scope.prlEconomicalCodeInfo = entity;
        //$scope.prlEconomicalCodeInfoList = [];
        //$scope.prlallowdeductinfos = PrlAllowDeductInfo.query();
        $scope.load = function(id) {
            PrlEconomicalCodeInfo.get({id : id}, function(result) {
                $scope.prlEconomicalCodeInfo = result;
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
            $scope.$emit('stepApp:prlEconomicalCodeInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('prlEconomicalCodeInfo');
        };

        $scope.saveCounter = 0;
        $scope.saveCounterActual = 0;
        var onSaveModelSuccess = function (result) {
            $scope.saveCounter++;
            console.log("Success: counter: "+$scope.saveCounter+", actual: "+$scope.saveCounterActual);
            if($scope.saveCounter == $scope.saveCounterActual)
            {
                $scope.$emit('stepApp:prlEconomicalCodeInfoUpdate');
                $scope.isSaving = false;
                $state.go('prlEconomicalCodeInfo');
                $scope.updatMessage = "Data Updated Successfully!!!";
            }
        };
        var onSaveModelError = function (result) {
            $scope.saveCounter++;
            console.log("Failed: counter: "+$scope.saveCounter+", actual: "+$scope.saveCounterActual);
            if($scope.saveCounter == $scope.saveCounterActual)
            {
                $scope.isSaving = false;
                $state.go('prlEconomicalCodeInfo');
                $scope.updatMessage = "Data Updated Successfully!!!";
            }
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.prlEconomicalCodeInfo.updateBy = $scope.loggedInUser.id;
            $scope.prlEconomicalCodeInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.prlEconomicalCodeInfo.id != null)
            {
                PrlEconomicalCodeInfo.update($scope.prlEconomicalCodeInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.prlEconomicalCodeInfo.updated');
            }
            else {
                $scope.prlEconomicalCodeInfo.createBy = $scope.loggedInUser.id;
                $scope.prlEconomicalCodeInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PrlEconomicalCodeInfo.save($scope.prlEconomicalCodeInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.prlEconomicalCodeInfo.created');
            }
        };

        $scope.saveAllEconomicalCodeSetup = function ()
        {
            console.log("Saving Economical Code list");
            if($scope.duplicateCodeFound==false)
            {
                $scope.saveCounter = 0;
                $scope.saveCounterActual = 0;
                $scope.updatMessage = "";
                angular.forEach($scope.prlEconomicalCodeInfoList,function(economicalInfo)
                {
                    console.log("Saving Economical Code Info name: "+economicalInfo.codeName);
                    if(economicalInfo.codeName != null && economicalInfo.codeName.length > 0)
                    {
                        $scope.saveCounterActual++;
                        economicalInfo.updateBy = $scope.loggedInUser.id;
                        economicalInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                        if (economicalInfo.id != null)
                        {
                            PrlEconomicalCodeInfo.update(economicalInfo, onSaveModelSuccess, onSaveModelError);
                            $rootScope.setWarningMessage('stepApp.prlEconomicalCodeInfo.updated');
                        }
                        else {
                            economicalInfo.createBy = $scope.loggedInUser.id;
                            economicalInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                            PrlEconomicalCodeInfo.save(economicalInfo, onSaveModelSuccess, onSaveModelError);
                            $rootScope.setSuccessMessage('stepApp.prlEconomicalCodeInfo.created');
                        }
                    }
                    else
                    {
                        console.log("Save ignoring");
                    }
                });
            }
            else
            {
                console.log("Already Exist Code!!!");
            }
        };

        $scope.loadAllEconomicalCodeList = function()
        {

            console.log("loadAllEconomicalCodeList");

            PrlEconomicalCodeInfoByFilter.query({type: 'all'}, function (result)
            {
                console.log("loadedAllEconomicalCodeList2: "+result.length);
                $scope.prlEconomicalCodeInfoList = result;

            }, function (response)
            {
                console.log("error: "+response);
            });
        };

        $scope.duplicateCodeFound = false;
        $scope.checkEconomicalCodeUniqueness = function(modelInfo, indx)
        {
            var isCodeMatched = false;
            if(modelInfo.codeName!=null)
            {
                $scope.doFade = false;
                $scope.showError = false;
                var bscIndx = 0;
                $scope.duplicateCodeFound = false;

                angular.forEach($scope.prlEconomicalCodeInfoList,function(basicObj)
                {
                    //console.log("watching > code:"+bscIndx+", code: "+basicObj.codeName+", model: "+modelInfo.codeName+", basId: "+basicObj.id+", modelId: "+modelInfo.id);
                    if(bscIndx != indx  && modelInfo.codeName == basicObj.codeName)
                    {
                        isCodeMatched = true;
                        $scope.duplicateCodeFound = true;
                        $scope.showError = true;
                        $scope.errorMessage = 'Economical Code already exist!!!';
                        $timeout(function(){
                            $scope.doFade = true;
                            $scope.errorMessage = '';
                        }, 2500);
                    }
                    bscIndx = bscIndx + 1;
                });
                return isCodeMatched;
            }
        };

        $scope.showError = false;
        $scope.doFade = false;
        $scope.errorMessage = '';

        $timeout(function()
        {
            console.log("Loading all economical code list: ");
            $scope.loadAllEconomicalCodeList();
        }, 400);

        $scope.clear = function() {

        };
}]);
