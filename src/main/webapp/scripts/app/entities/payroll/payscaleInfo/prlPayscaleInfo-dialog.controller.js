'use strict';

angular.module('stepApp').controller('PrlPayscaleInfoDialogController',
    ['$rootScope','$scope', '$stateParams', '$state', 'entity', '$timeout', 'PrlPayscaleInfo', 'HrGradeSetupByStatus', 'HrGazetteSetupByStatus','User','Principal','DateUtils','PrlPayscaleBasicInfoByPayscale','PrlPayscaleBasicInfo','PrlPayscaleAllowanceInfoByPsGrd','PrlPayscaleAllowanceInfo','PrlPayscaleInfoCheckByGrdGztNotSelf',
        function($rootScope, $scope, $stateParams, $state, entity, $timeout, PrlPayscaleInfo, HrGradeSetupByStatus, HrGazetteSetupByStatus, User, Principal, DateUtils, PrlPayscaleBasicInfoByPayscale, PrlPayscaleBasicInfo,PrlPayscaleAllowanceInfoByPsGrd,PrlPayscaleAllowanceInfo,PrlPayscaleInfoCheckByGrdGztNotSelf) {

        $scope.prlPayscaleInfo = entity;
        $scope.hrgradesetups = HrGradeSetupByStatus.get({stat:'true'});
        //$scope.hrgazettesetups = HrGazetteSetup.query();
        $scope.hrgazettesetups = HrGazetteSetupByStatus.get({stat:'true'});
        $scope.load = function(id) {
            PrlPayscaleInfo.get({id : id}, function(result) {
                $scope.prlPayscaleInfo = result;
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

        $scope.prlBasicPayscaleList = [];

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:prlPayscaleInfoUpdate', result);
            $scope.isSaving = false;
            console.log(JSON.stringify(result));
            $scope.prlPayscaleInfo.id = result.id;
            $scope.loadAllowanceList();
            //$rootScope.setSuccessMessage('stepApp.hrEmpChildrenInfo.created');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
           // $rootScope.setWarningMessage('stepApp.hrEmpChildrenInfo.updated');
        };

        $scope.savePayscaleInfo = function ()
        {
            //console.log("Saving PayScale Information");
            $scope.isSaving = true;
            $scope.prlPayscaleInfo.updateBy = $scope.loggedInUser.id;
            $scope.prlPayscaleInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.prlPayscaleInfo.id != null)
            {
                PrlPayscaleInfo.update($scope.prlPayscaleInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.prlPayscaleInfo.updated');
            }
            else
            {
                $scope.prlPayscaleInfo.createBy = $scope.loggedInUser.id;
                $scope.prlPayscaleInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PrlPayscaleInfo.save($scope.prlPayscaleInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.prlPayscaleInfo.created');
            }
        };

        var onBasicSaveSuccess = function (result) {
            $scope.$emit('stepApp:prlPayscaleInfoUpdate', result);
            $scope.isSaving = false;
            //console.log(JSON.stringify(result));
            $scope.prlBasicPayscaleList[$scope.selectedIndex].id=result.id;
            $scope.basicPayscaleSrl = result.serialNumber;
            $scope.basicPayscaleBasic = result.basicAmount;
            $scope.changeProfileMode($scope.prlBasicPayscaleList[$scope.selectedIndex]);
            $rootScope.setSuccessMessage('stepApp.prlPayscaleBasicInfo.updated');
        };

        var onBasicSaveError = function (result) {
            $scope.isSaving = false;
            $rootScope.setWarningMessage('stepApp.prlPayscaleBasicInfo.warning');
        };

        $scope.savePayScaleBasic = function (payscaleBasicInfo, index)
        {
            //console.log(JSON.stringify(payscaleBasicInfo));

            $scope.selectedIndex = index;

            var isSerialMatched = $scope.checkSerialUniqueness(payscaleBasicInfo, index);
            console.log("Uniqueness checking: serial: "+isSerialMatched);
            if(isSerialMatched==false)
            {
                console.log("Serial Already exist checking: "+isSerialMatched);
                var isBasicMatched = $scope.checkBasicUniqueness(payscaleBasicInfo, index);
                if(isBasicMatched==true)
                {
                    console.log("Basic Already exist checking: "+isBasicMatched);
                }
                else
                {
                    payscaleBasicInfo.isSaving = true;
                    payscaleBasicInfo.updateBy = $scope.loggedInUser.id;
                    payscaleBasicInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                    if (payscaleBasicInfo.id != null)
                    {
                        PrlPayscaleBasicInfo.update(payscaleBasicInfo, onBasicSaveSuccess, onBasicSaveError);
                        $rootScope.setWarningMessage('stepApp.payscaleBasicInfo.updated');
                    } else
                    {
                        payscaleBasicInfo.createBy = $scope.loggedInUser.id;
                        payscaleBasicInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                        PrlPayscaleBasicInfo.save(payscaleBasicInfo, onBasicSaveSuccess, onBasicSaveError);
                        $rootScope.setSuccessMessage('stepApp.payscaleBasicInfo.created');
                    }
                }
            }

        };

        var onAllowanceSaveSuccess = function (result) {
            $scope.$emit('stepApp:prlPayscaleInfoUpdate', result);
            $scope.isSaving = false;
            console.log(JSON.stringify(result));
            $scope.prlPayscaleAllowanceList[$scope.selectedIndexAlwn].id=result.id;
            $rootScope.setSuccessMessage('stepApp.prlPayscaleBasicInfo.updated');
        };

        var onAllowanceSaveError = function (result) {
            $scope.isSaving = false;
            $rootScope.setWarningMessage('stepApp.prlPayscaleBasicInfo.warning');
        };
        $scope.savePayscaleAllowanceData = function (payscaleAllowanceObj, index)
        {
            console.log("saving allowance data");
            $scope.isSaving = true;
            $scope.selectedIndexAlwn = index;
            payscaleAllowanceObj.updateBy = $scope.loggedInUser.id;
            payscaleAllowanceObj.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            payscaleAllowanceObj.payscaleInfo = $scope.prlPayscaleInfo;
            if (payscaleAllowanceObj.id != null)
            {
                PrlPayscaleAllowanceInfo.update(payscaleAllowanceObj, onAllowanceSaveSuccess, onAllowanceSaveError);
                $rootScope.setWarningMessage('stepApp.prlPayscaleAllowanceInfo.updated');
            } else
            {
                payscaleBasicInfo.createBy = $scope.loggedInUser.id;
                payscaleBasicInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PrlPayscaleAllowanceInfo.save(payscaleAllowanceObj, onAllowanceSaveSuccess, onAllowanceSaveError);
                $rootScope.setSuccessMessage('stepApp.prlPayscaleAllowanceInfo.created');
            }
        };

        $scope.loadAllowanceList = function()
        {

            if($scope.prlPayscaleInfo.id != null)
            {
                console.log("loadAllowanceList payscaleid: "+$scope.prlPayscaleInfo.id+", gradeId: "+$scope.prlPayscaleInfo.gradeInfo.id);
                PrlPayscaleAllowanceInfoByPsGrd.query({psid: $scope.prlPayscaleInfo.id,grdid:$scope.prlPayscaleInfo.gradeInfo.id}, function (result)
                {
                    console.log("PayScaleAllowance: "+JSON.stringify(result));
                    $scope.prlPayscaleAllowanceList = result;

                }, function (response)
                {
                    console.log("error: "+response);
                })
            }
        };

        $scope.loadBasicSalaryList = function()
        {
            //console.log("LoadBasicInfo");
            if($scope.prlPayscaleInfo.id != null)
            {
                //console.log("loadBasicSalaryList payscaleid: "+$scope.prlPayscaleInfo.id+", viewMode: "+$scope.viewMode);
                PrlPayscaleBasicInfoByPayscale.query({psid: $scope.prlPayscaleInfo.id}, function (result)
                {
                    //console.log("PayscaleBasicList: "+JSON.stringify(result));
                    $scope.prlBasicPayscaleList = result;
                    angular.forEach($scope.prlBasicPayscaleList,function(modelInfo)
                    {
                        modelInfo.viewMode = true;
                        modelInfo.viewModeText = "Edit";
                    });
                }, function (response)
                {
                    console.log("error: "+response);
                    $scope.hasProfile = false;
                    $scope.addMode = true;
                    //$scope.loadEmployee();
                })
            }
        };

        $timeout(function()
        {
            console.log("Loading payscale basic list: ");
            $scope.loadBasicSalaryList();

            console.log("Loading payscale allowance list: ");
            $scope.loadAllowanceList();
        }, 1000);

        $scope.addMore = function()
        {
            $scope.prlBasicPayscaleList.push(
                {
                    viewMode:false,
                    viewModeText:'Cancel',
                    serialNumber: null,
                    basicAmount: null,
                    activeStatus: true,
                    createDate: null,
                    createBy: null,
                    updateDate: null,
                    updateBy: null,
                    payscaleInfo: $scope.prlPayscaleInfo,
                    id: null
                }
            );
        };

        $scope.checkSerialUniqueness = function(modelInfo, indx)
        {
            var isSrlMatched = false;
            $scope.doFade = false;
            $scope.showError = false;
            //console.log("checking for basic:index: "+indx+", json: "+JSON.stringify(modelInfo));
            var srlIndx = 0;
            angular.forEach($scope.prlBasicPayscaleList,function(basicObj)
            {
                //console.log("watching > "+srlIndx+", serial: "+basicObj.serialNumber+", model: "+modelInfo.serialNumber+", basId: "+basicObj.id+", modelId: "+modelInfo.id);
                if(srlIndx != indx && modelInfo.serialNumber == basicObj.serialNumber)
                {
                    isSrlMatched = true;
                    $scope.showError = true;
                    $scope.errorMessage = 'Serial Number already exist!!!';
                    $timeout(function(){
                        $scope.doFade = true;
                        $scope.errorMessage = '';
                    }, 2500);
                }
                srlIndx = srlIndx + 1;
            });
            return isSrlMatched;
        };

        $scope.checkBasicUniqueness = function(modelInfo, indx)
        {
            var isBscMatched = false;
            $scope.doFade = false;
            $scope.showError = false;
            var bscIndx = 0;
            angular.forEach($scope.prlBasicPayscaleList,function(basicObj)
            {
                //console.log("watching > basic:"+bscIndx+", basic: "+basicObj.basicAmount+", model: "+modelInfo.basicAmount+", basId: "+basicObj.id+", modelId: "+modelInfo.id);
                if(bscIndx != indx  && modelInfo.basicAmount == basicObj.basicAmount)
                {
                    isBscMatched = true;
                    $scope.showError = true;
                    $scope.errorMessage = 'Basic Amount already exist!!!';
                    $timeout(function(){
                        $scope.doFade = true;
                        $scope.errorMessage = '';
                    }, 2500);
                }
                bscIndx = bscIndx + 1;
            });
            return isBscMatched;
        };

        $scope.basicPayscaleSrl = 0;$scope.basicPayscaleBasic = 0;
        $scope.changeProfileMode = function (modelInfo)
        {
            if(modelInfo.viewMode)
            {
                modelInfo.viewMode = false;
                modelInfo.viewModeText = "Cancel";
                $scope.basicPayscaleSrl = modelInfo.serialNumber;
                $scope.basicPayscaleBasic = modelInfo.basicAmount;
            }
            else
            {
                modelInfo.viewMode = true;
                if(modelInfo.id==null)
                {
                    if($scope.prlBasicPayscaleList.length > 1)
                    {
                        var indx = $scope.prlBasicPayscaleList.indexOf(modelInfo);
                        $scope.prlBasicPayscaleList.splice(indx, 1);
                    }
                    else
                    {
                        modelInfo.viewModeText = "Add";
                    }
                }
                else
                {
                    modelInfo.viewModeText = "Edit";
                    modelInfo.serialNumber = $scope.basicPayscaleSrl;
                    modelInfo.basicAmount = $scope.basicPayscaleBasic;
                }

            }
        };

        $scope.payscaleAlreadyExist = false;
        $scope.payscaleValidationChecking = false;
        $scope.checkPayscaleUniqueness = function()
        {
            var pscaleId = 0; var isValidData = false;
            if($scope.prlPayscaleInfo != null && $scope.prlPayscaleInfo.id != null)
            {
                pscaleId = $scope.prlPayscaleInfo.id;
            }
            else{
                pscaleId = -1;
            }
            console.log(" psid: "+pscaleId);
            if($scope.prlPayscaleInfo.gazetteInfo.id != null && $scope.prlPayscaleInfo.gradeInfo.id != null)
            {
                $scope.payscaleValidationChecking = true;
                console.log("gaztid: "+$scope.prlPayscaleInfo.gazetteInfo.id+", gradeid: "+$scope.prlPayscaleInfo.gradeInfo.id+", psid: "+pscaleId);

                PrlPayscaleInfoCheckByGrdGztNotSelf.get({gztid:$scope.prlPayscaleInfo.gazetteInfo.id, grdid: $scope.prlPayscaleInfo.gradeInfo.id, psid:pscaleId}, function(result)
                {
                    console.log(JSON.stringify(result));
                    $scope.isSaving = !result.isValid;
                    $scope.payscaleValidationChecking = false;
                    if(result.isValid)
                    {
                        console.log("valid");
                        $scope.payscaleAlreadyExist = false;
                    }
                    else
                    {
                        console.log("not valid");
                        $scope.payscaleAlreadyExist = true;
                    }
                },function(response)
                {
                    console.log("data connection failed");
                    $scope.payscaleValidationChecking = false;
                });
            }
        };


        $scope.showError = false;
        $scope.doFade = false;
        $scope.errorMessage = '';

        $scope.clear = function() {

        };
}]);
