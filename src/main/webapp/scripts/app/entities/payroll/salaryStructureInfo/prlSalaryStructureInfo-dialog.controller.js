'use strict';

angular.module('stepApp').controller('PrlSalaryStructureInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity','PrlSalaryStructureInfo','HrGazetteSetupByStatus', 'PrlPayscaleInfo', 'PrlPayscaleBasicInfoByPayscale', 'HrEmployeeInfo','User','Principal','DateUtils','PrlPayscaleInfoByGrdGzzt','PrlSalaryStructureInfoByFilter','PrlSalaryAllowDeducInfo','PrlSalaryStructureInfoByEmp',
        function($scope, $rootScope, $stateParams, $state, entity, PrlSalaryStructureInfo, HrGazetteSetupByStatus, PrlPayscaleInfo, PrlPayscaleBasicInfoByPayscale, HrEmployeeInfo, User, Principal, DateUtils, PrlPayscaleInfoByGrdGzzt,PrlSalaryStructureInfoByFilter,PrlSalaryAllowDeducInfo,PrlSalaryStructureInfoByEmp) {

        $scope.prlSalaryStructureInfo = entity;
        $scope.prlpayscaleinfos = [];//PrlPayscaleInfo.query();
        $scope.hrgazettesetups = HrGazetteSetupByStatus.get({stat:'true'});
        $scope.prlpayscalebasicinfos = [];//PrlPayscaleBasicInfo.query();
        $scope.hremployeeinfos = HrEmployeeInfo.query({size:500});
        $scope.currentDate = new Date();

        $scope.currentSalaryStructureId = 0;
        $scope.currentEmployeeId        = 0;

        $scope.load = function()
        {
            var salStId = 0;
            if($stateParams.id)
            {
                console.log("param id");
                salStId = $stateParams.id;
            }
            else if($scope.prlSalaryStructureInfo && $scope.prlSalaryStructureInfo.id != null)
            {
                console.log("object id");
                salStId = $scope.prlSalaryStructureInfo.id;
            }
            console.log("ParamId: "+$stateParams.id+", objectId: "+$scope.prlSalaryStructureInfo.id+", retrievedId: "+salStId);
            if(salStId > 0)
            {
                PrlSalaryStructureInfo.get({id : salStId}, function(result)
                {
                    $scope.prlSalaryStructureInfo = result;
                    $scope.currentSalaryStructureId = result.id;
                    $scope.currentEmployeeId        = result.employeeInfo.id;
                    console.log("Edit mode data SSId: "+$scope.currentSalaryStructureId+", EmpId: "+$scope.currentEmployeeId);
                    //console.log("Structure: "+JSON.stringify($scope.prlSalaryStructureInfo));
                    PrlPayscaleBasicInfoByPayscale.query({psid: $scope.prlSalaryStructureInfo.payscaleInfo.id}, function (result2)
                    {
                        //console.log("PayscaleBasicList: "+JSON.stringify(result2));
                        $scope.prlpayscalebasicinfos = result2;
                    }, function (response)
                    {
                        console.log("error: "+response);
                    });

                    console.log("EditMode: PsID: "+result.payscaleInfo.id+", GrdID: "+result.employeeInfo.designationInfo.gradeInfo.id+", EmpId: "+result.employeeInfo.id);
                    PrlSalaryStructureInfoByFilter.query({psid: result.payscaleInfo.id,
                        grdid: result.employeeInfo.designationInfo.gradeInfo.id,
                        empid: result.employeeInfo.id}, function (result3)
                    {
                        //console.log("SalaryAllowDeducInfoList: "+JSON.stringify(result3));
                        $scope.prlSalAllowDeductList = result3;
                        angular.forEach($scope.prlSalAllowDeductList,function(salAlDdDto)
                        {
                            if(salAlDdDto.allowDeducType=='Allowance' && salAlDdDto.fixedBasic==false)
                            {
                                salAlDdDto.basicMinimumTmp = salAlDdDto.basicMinimum;
                                salAlDdDto.basicMaximumTmp = salAlDdDto.basicMaximum;
                                if($scope.prlSalaryStructureInfo.payscaleBasicInfo != null)
                                {
                                    salAlDdDto.basicMinimum = $scope.prlSalaryStructureInfo.payscaleBasicInfo.basicAmount * (salAlDdDto.basicMinimumTmp / 100);
                                    salAlDdDto.basicMaximum= $scope.prlSalaryStructureInfo.payscaleBasicInfo.basicAmount * (salAlDdDto.basicMaximumTmp / 100);
                                }
                            }
                        });

                    }, function (response)
                    {
                        console.log("error: "+response);
                    });

                });
            }
        };
        $scope.load();

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

        $scope.loadPayscaleByGazzeteAndGrade = function()
        {
            console.log("gradeId: "+$scope.prlSalaryStructureInfo.employeeInfo.gradeInfo.id+", gazzeteId: "+$scope.prlSalaryStructureInfo.gazetteInfo.id);
            if($scope.prlSalaryStructureInfo.employeeInfo.gradeInfo.id && $scope.prlSalaryStructureInfo.gazetteInfo.id)
            {
                $scope.payscaleMessage = "";
                PrlPayscaleInfoByGrdGzzt.get(
                    {gztid: $scope.prlSalaryStructureInfo.gazetteInfo.id,
                     grdid: $scope.prlSalaryStructureInfo.employeeInfo.designationInfo.gradeInfo.id}, function(result)
                {
                    //$scope.prlSalaryStructureInfo = result;
                    //console.log("NewPayScaleInfo: "+JSON.stringify(result));
                    if(result.id)
                    {
                        PrlPayscaleBasicInfoByPayscale.query({psid: result.id}, function (result2)
                        {
                            //console.log("NewPayscaleBasicList: "+JSON.stringify(result2));
                            $scope.prlpayscalebasicinfos = result2;
                        }, function (response)
                        {
                            console.log("error: "+response);
                        });

                        console.log("NewMode: PsID: "+result.id+", GrdID: "+$scope.prlSalaryStructureInfo.employeeInfo.designationInfo.gradeInfo.id+", EmpId: "+$scope.prlSalaryStructureInfo.employeeInfo.id);

                        PrlSalaryStructureInfoByFilter.query({psid: result.id,
                            grdid: $scope.prlSalaryStructureInfo.employeeInfo.designationInfo.gradeInfo.id,
                            empid: $scope.prlSalaryStructureInfo.employeeInfo.id}, function (result3)
                        {
                            //console.log("SalaryAllowDeducInfoList: "+JSON.stringify(result3));
                            $scope.prlSalAllowDeductList = result3;
                            angular.forEach($scope.prlSalAllowDeductList,function(salAlDdDto)
                            {
                                if(salAlDdDto.allowDeducType=='Allowance' && salAlDdDto.fixedBasic==false)
                                {
                                    salAlDdDto.basicMinimumTmp = salAlDdDto.basicMinimum;
                                    salAlDdDto.basicMaximumTmp = salAlDdDto.basicMaximum;
                                    if($scope.prlSalaryStructureInfo.payscaleBasicInfo != null)
                                    {
                                        salAlDdDto.basicMinimum = $scope.prlSalaryStructureInfo.payscaleBasicInfo.basicAmount * (salAlDdDto.basicMinimumTmp / 100);
                                        salAlDdDto.basicMaximum= $scope.prlSalaryStructureInfo.payscaleBasicInfo.basicAmount * (salAlDdDto.basicMaximumTmp / 100);

                                        //salAlDdDto.salaryAllowDeducInfo.allowDeducValue = salAlDdDto.basicMinimum;
                                    }
                                }
                            });
                        }, function (response)
                        {
                            console.log("errorPrlSalaryStructureInfoByFilter: "+response);
                        });
                    }
                    else{
                        console.log("NoPaycale available for given info.: ");
                        $scope.payscaleMessage = "Payscale is not available for these information!!!";
                        $scope.prlpayscalebasicinfos = [];
                    }

                });
            }
        };

        $scope.calculateAllowanceBasedOnBasic = function ()
        {
            console.log("currentBasic: "+$scope.prlSalaryStructureInfo.payscaleBasicInfo.basicAmount);
            angular.forEach($scope.prlSalAllowDeductList,function(salAlDdDto)
            {
                console.log("Type: "+salAlDdDto.allowDeducType+", Fixed: "+salAlDdDto.fixedBasic+", min: "+salAlDdDto.basicMinimum+", max: "+salAlDdDto.basicMaximum);
                if(salAlDdDto.allowDeducType=='Allowance' && salAlDdDto.fixedBasic==false)
                {
                    salAlDdDto.basicMinimum = $scope.prlSalaryStructureInfo.payscaleBasicInfo.basicAmount * (salAlDdDto.basicMinimumTmp / 100);
                    salAlDdDto.basicMaximum= $scope.prlSalaryStructureInfo.payscaleBasicInfo.basicAmount * (salAlDdDto.basicMaximumTmp / 100);

                    salAlDdDto.salaryAllowDeducInfo.allowDeducValue = salAlDdDto.basicMinimum;
                }
                if(salAlDdDto.allowDeducType=='Allowance' && salAlDdDto.fixedBasic)
                {
                    salAlDdDto.salaryAllowDeducInfo.allowDeducValue = salAlDdDto.basicMinimum;
                }
            });
        };

        $scope.calendar = {
            opened: {},
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:prlSalaryStructureInfoUpdate', result);
            $rootScope.setWarningMessage('stepApp.salaryAllowDeducInfo.updated');
            $scope.prlSalaryStructureInfo.id = result.id;
            $scope.load();
            $scope.isSaving = false;
            //$state.go('prlSalaryStructureInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        var onAllowDeducSaveSuccess = function (result)
        {
            $scope.allowDeducSaveCounter = $scope.allowDeducSaveCounter + 1;
            console.log("counter: "+$scope.allowDeducSaveCounter +", list: "+$scope.allowDeductToalList);
            if($scope.allowDeducSaveCounter == $scope.allowDeductToalList)
            {
                $scope.$emit('stepApp:prlSalaryStructureInfoUpdate', result);
                $scope.isSaving2 = false;
                $state.go('prlSalaryStructureInfo');
            }
        };

        var onAllowDeducSaveError = function (result) {
            $scope.isSaving2 = false;
        };
        $scope.allowDeducSaveCounter = 0;
        $scope.allowDeductToalList = 0;
        $scope.saveSalaryAllowanceDeduction = function ()
        {
            $scope.isSaving2 = true;

            $scope.allowDeductToalList = $scope.prlSalAllowDeductList.length;
            $scope.allowDeducSaveCounter = 0;
            angular.forEach($scope.prlSalAllowDeductList,function(salAlDdDto)
            {
                console.log("Saving: "+salAlDdDto.allowDeducType+", min: "+salAlDdDto.basicMinimum+", max: "+salAlDdDto.basicMaximum+", listLen: "+$scope.allowDeductToalList);
                salAlDdDto.salaryAllowDeducInfo.updateBy = $scope.loggedInUser.id;
                salAlDdDto.salaryAllowDeducInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                salAlDdDto.salaryAllowDeducInfo.salaryStructureInfo = $scope.prlSalaryStructureInfo;
                salAlDdDto.salaryAllowDeducInfo.activeStatus = 1;
                if (salAlDdDto.salaryAllowDeducInfo.id != null)
                {
                    PrlSalaryAllowDeducInfo.update(salAlDdDto.salaryAllowDeducInfo, onAllowDeducSaveSuccess, onAllowDeducSaveError);
                    $rootScope.setWarningMessage('stepApp.salaryAllowDeducInfo.updated');
                }
                else
                {
                    salAlDdDto.salaryAllowDeducInfo.createBy = $scope.loggedInUser.id;
                    salAlDdDto.salaryAllowDeducInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    PrlSalaryAllowDeducInfo.save(salAlDdDto.salaryAllowDeducInfo, onAllowDeducSaveSuccess, onAllowDeducSaveError);
                    $rootScope.setSuccessMessage('stepApp.salaryAllowDeducInfo.created');
                }
            });
            //if($scope.allowDeductToalList < count) $scope.isSaving2 = false;
        };

        $scope.saveSalaryStructure = function ()
        {
            $scope.isSaving = true;
            $scope.prlSalaryStructureInfo.updateBy = $scope.loggedInUser.id;
            $scope.prlSalaryStructureInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            $scope.prlSalaryStructureInfo.basicAmount = $scope.prlSalaryStructureInfo.payscaleBasicInfo.basicAmount;
            $scope.prlSalaryStructureInfo.payscaleInfo = $scope.prlSalaryStructureInfo.payscaleBasicInfo.payscaleInfo;
            //console.log("Saving: Structure: "+JSON.stringify($scope.prlSalaryStructureInfo));
            if ($scope.prlSalaryStructureInfo.id != null)
            {
                PrlSalaryStructureInfo.update($scope.prlSalaryStructureInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.prlSalaryStructureInfo.updated');
            }
            else
            {
                $scope.prlSalaryStructureInfo.createBy = $scope.loggedInUser.id;
                $scope.prlSalaryStructureInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PrlSalaryStructureInfo.save($scope.prlSalaryStructureInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.prlSalaryStructureInfo.created');
            }
        };

        $scope.counter=0;
        $scope.salaryStructureAlreadyExist = false;
        $scope.salaryStructureValidationChecking = false;

        $scope.$watch('prlSalaryStructureInfo.employeeInfo', function()
        {
            $scope.counter=$scope.counter+1;
            var salStrucId = 0;
            console.log("ngwatch - "+$scope.counter);
            if($scope.prlSalaryStructureInfo.employeeInfo!=null && $scope.prlSalaryStructureInfo.employeeInfo.id!=null)
            {
                console.log("ngwatch - "+$scope.counter+", empId: "+$scope.prlSalaryStructureInfo.employeeInfo.fullName);

                if($scope.prlSalaryStructureInfo != null && $scope.prlSalaryStructureInfo.id != null)
                {
                    salStrucId  = $scope.prlSalaryStructureInfo.id;
                }

                if($scope.currentSalaryStructureId > 0 && $scope.currentEmployeeId >0 &&
                    $scope.currentSalaryStructureId == salStrucId &&
                    $scope.currentEmployeeId == $scope.prlSalaryStructureInfo.employeeInfo.id)
                {
                    console.log("Same data in edit mode SSId: "+$scope.currentSalaryStructureId+", EmpId: "+$scope.currentEmployeeId);
                    $scope.salaryStructureAlreadyExist = false;
                }
                else
                {
                    if($scope.prlSalaryStructureInfo != null && $scope.prlSalaryStructureInfo.employeeInfo != null)
                    {
                        $scope.salaryStructureValidationChecking = true;
                        console.log("empId: "+$scope.prlSalaryStructureInfo.employeeInfo.id+", salStrucId: "+salStrucId);
                        PrlSalaryStructureInfoByEmp.get({salid:salStrucId, empid: $scope.prlSalaryStructureInfo.employeeInfo.id}, function(result)
                        {
                            console.log(JSON.stringify(result));
                            $scope.isSaving = !result.isValid;
                            $scope.salaryStructureValidationChecking = false;
                            if(result.isValid)
                            {
                                console.log("valid");
                                $scope.salaryStructureAlreadyExist = false;
                            }
                            else
                            {
                                console.log("not valid");
                                $scope.salaryStructureAlreadyExist = true;
                            }
                        },function(response)
                        {
                            console.log("data connection failed");
                            $scope.salaryStructureValidationChecking = false;
                        });
                    }
                    else
                    {
                        $scope.salaryStructureValidationChecking = false;
                    }
                }
            }
        });

        $scope.clear = function() {};
}]);
