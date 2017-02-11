'use strict';

angular.module('stepApp').controller('PrlEmpGeneratedSalInfoProfileController',
    ['$scope', '$stateParams', '$state','$filter','$rootScope', 'entity', 'PrlEmpGeneratedSalInfo', 'PrlGeneratedSalaryInfo', 'PrlSalaryStructureInfo', 'HrEmployeeInfo','User','Principal','DateUtils','PrlEmpGeneratedSalInfoFilter','PrlEmpGenSalDetailInfo',
        function($scope, $stateParams, $state, $filter, $rootScope, entity, PrlEmpGeneratedSalInfo, PrlGeneratedSalaryInfo, PrlSalaryStructureInfo, HrEmployeeInfo, User, Principal, DateUtils,PrlEmpGeneratedSalInfoFilter,PrlEmpGenSalDetailInfo) {

        $scope.prlEmpGeneratedSalInfo = entity;
        $scope.prlgeneratedsalaryinfos = PrlGeneratedSalaryInfo.query();
        $scope.prlsalarystructureinfos = PrlSalaryStructureInfo.query();
        $scope.hremployeeinfos = HrEmployeeInfo.query();
        $scope.load = function(id) {
            PrlEmpGeneratedSalInfo.get({id : id}, function(result) {
                $scope.prlEmpGeneratedSalInfo = result;
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

        $scope.yearList = $rootScope.generateYearList(new Date().getFullYear()-1, 5);

        $scope.allowanceExtraList =   [];
        $scope.deductionExtraList =   [];
        $scope.tempSalaryAllowDeducList = [];
        $scope.isLoading = false;
        $scope.loadEmployeeGeneratedSalary = function()
        {
            $scope.isLoading = true;
            $scope.checkMonth();

            var empId = $scope.hrEmployeeInfo.id;
            console.log("year: "+$scope.prlEmpGeneratedSalInfo.yearName+", mon: "+$scope.prlEmpGeneratedSalInfo.month+", empId: "+empId);
            $scope.allowanceExtraList = [];
            $scope.deductionExtraList = [];
            PrlEmpGeneratedSalInfoFilter.get({year: $scope.prlEmpGeneratedSalInfo.yearName,
                month: $scope.prlEmpGeneratedSalInfo.month,
                empid: empId, saltype:'MONTHLY'}, function (resultDto)
            {
                //console.log("GeneratedSalaryDto: "+JSON.stringify(resultDto));
                $scope.generatedSalaryDto = resultDto;
                $scope.tempSalaryAllowDeducList = [];
                if($scope.generatedSalaryDto !=null && $scope.generatedSalaryDto.salaryDetailList != null)
                {
                    //console.log("GeneratedSalaryDtoList: "+JSON.stringify($scope.generatedSalaryDto.salaryDetailList));
                    $scope.tempSalaryAllowDeducList = angular.copy($scope.generatedSalaryDto.salaryDetailList);

                    angular.forEach($scope.generatedSalaryDto.salaryDetailList,function(salaryDtlInfo)
                    {
                        console.log("alddtype: "+salaryDtlInfo.allowDeducType+", name: "+salaryDtlInfo.allowDeducName+", val: "+salaryDtlInfo.allowDeducValue);
                        if(salaryDtlInfo.allowDeducType=='AllowanceExtra')
                        {
                            $scope.allowanceExtraList.push(salaryDtlInfo);
                        }
                        if(salaryDtlInfo.allowDeducType=='DeductionExtra')
                        {
                            $scope.deductionExtraList.push(salaryDtlInfo);
                        }
                    });
                }

                if($scope.isCurrentMonthSelected === true)
                {
                    for (var i=$scope.allowanceExtraList.length; i<5; i++) {
                        $scope.allowanceExtraList.push($scope.addExtraItem('AllowanceExtra', $scope.generatedSalaryDto.employeeSalaryInfo));
                    }

                    for (var i=$scope.deductionExtraList.length; i<5; i++) {
                        $scope.deductionExtraList.push($scope.addExtraItem('DeductionExtra', $scope.generatedSalaryDto.employeeSalaryInfo));
                    }
                }
                console.log("PST: AllowanceExtraLen: "+$scope.allowanceExtraList.length+", DeductionExtraLen: "+$scope.deductionExtraList.length);
                $scope.isLoading = false;
            }, function (response)
            {
                console.log("error: "+response);
            });
        };


        $scope.addExtraItem = function(itemType, empSalInfp)
        {
            return {
                employeeSalaryInfo: empSalInfp,
                allowDeducType: itemType,
                allowDeducId: null,
                allowDeducName: '',
                allowDeducValue: '',
                id: null
            };
        };
        $scope.allowDeducSaveCounter    = 0;
        $scope.allowDeducSaveTotal      = 0;
        $scope.saveAllowanceDeductionList = function ()
        {
            if($scope.generatedSalaryDto !=null && $scope.generatedSalaryDto.salaryDetailList != null)
            {
                //console.log("GeneratedSalaryDtoList: "+JSON.stringify($scope.generatedSalaryDto.salaryDetailList));
                //$scope.tempSalaryAllowDeducList;
                var counter = 0;
                $scope.allowDeducSaveCounter    = 0;
                $scope.allowDeducSaveTotal      = 0;
                angular.forEach($scope.generatedSalaryDto.salaryDetailList,function(salaryDtlInfo)
                {
                    console.log("Counter: "+counter+", totalSave: "+$scope.allowDeducSaveTotal);
                    var salDto = $scope.tempSalaryAllowDeducList[counter];
                    console.log("Tmp: alddtype: "+salDto.allowDeducType+", name: "+salDto.allowDeducName+", val: "+salDto.allowDeducValue);
                    console.log("Act: alddtype: "+salaryDtlInfo.allowDeducType+", name: "+salaryDtlInfo.allowDeducName+", val: "+salaryDtlInfo.allowDeducValue);

                    if(salaryDtlInfo.allowDeducType != 'AllowanceExtra' || salaryDtlInfo.allowDeducType != 'DeductionExtra')
                    {
                        if((salDto.allowDeducType != salaryDtlInfo.allowDeducType)
                            || (salDto.allowDeducName != salaryDtlInfo.allowDeducName)
                            || (salDto.allowDeducValue != salaryDtlInfo.allowDeducValue))
                        {
                            $scope.allowDeducSaveTotal      = $scope.allowDeducSaveTotal + 1;
                            console.log("Saving , Base Obj: ");
                            $scope.isSaving = true;
                            $scope.saveAllowDeducObject(salaryDtlInfo);
                        }
                        else
                        {
                            console.log("Not Saving, value same");
                        }
                    }
                    counter++;
                });
                angular.forEach($scope.allowanceExtraList, function(allowExtra)
                {
                    console.log("Allow: alddtype: "+allowExtra.allowDeducType+", name: "+allowExtra.allowDeducName+", val: "+allowExtra.allowDeducValue);
                    if(allowExtra.allowDeducName != null && allowExtra.allowDeducName.length > 1
                       && allowExtra.allowDeducValue != null && allowExtra.allowDeducValue > 0)
                    {
                        console.log("Saving , Obj allowance: ");
                        $scope.isSaving = true;
                        $scope.saveAllowDeducObject(allowExtra);
                    }
                    else
                    {
                        console.log("Not Saving AllowX, Empty Value");
                    }
                });

                angular.forEach($scope.deductionExtraList, function(allowExtra)
                {
                    console.log("Deduc: alddtype: "+allowExtra.allowDeducType+", name: "+allowExtra.allowDeducName+", val: "+allowExtra.allowDeducValue);
                    if(allowExtra.allowDeducName != null && allowExtra.allowDeducName.length > 1
                        && allowExtra.allowDeducValue != null && allowExtra.allowDeducValue > 0)
                    {
                        console.log("Saving deducX, Obj: ");
                        $scope.isSaving = true;
                        $scope.saveAllowDeducObject(allowExtra);
                    }
                    else
                    {
                        console.log("Not Saving DeducX, Empty Value");
                    }
                });
            }
        };

        $scope.saveAllowDeducObject = function(salaryDtlInfo)
        {
            salaryDtlInfo.updateBy = $scope.loggedInUser.id;
            salaryDtlInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if (salaryDtlInfo.id != null) {
                //PrlEmpGenSalDetailInfo.update(salaryDtlInfo, onAllowDeducSaveSuccess, onAllowDeducSaveError);
                PrlEmpGenSalDetailInfo.update(salaryDtlInfo);
                //$rootScope.setWarningMessage('stepApp.prlEmpGenSalDetailInfo.updated');
            } else {
                salaryDtlInfo.createBy = $scope.loggedInUser.id;
                salaryDtlInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                //PrlEmpGenSalDetailInfo.save(salaryDtlInfo, onAllowDeducSaveSuccess, onAllowDeducSaveError);
                PrlEmpGenSalDetailInfo.save(salaryDtlInfo);
                //$rootScope.setSuccessMessage('stepApp.prlEmpGenSalDetailInfo.created');
            }
        };

        var onAllowDeducSaveSuccess = function (result)
        {
            $scope.allowDeducSaveCounter = $scope.allowDeducSaveCounter + 1;
            console.log("counter: "+$scope.allowDeducSaveCounter +", list: "+$scope.allowDeducSaveTotal);
            if($scope.allowDeducSaveCounter == $scope.allowDeducSaveTotal)
            {
                $scope.$emit('stepApp:prlSalaryStructureInfoUpdate', result);
                $scope.isSaving = false;
                //$state.go('prlSalaryStructureInfo');
                console.log("Save Success");
            }
        };

        var onAllowDeducSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.loadProfile = function ()
        {
            console.log("loadEmployeeProfile");
            HrEmployeeInfo.get({id: 'my'}, function (result) {
                $scope.hrEmployeeInfo = result;
                $scope.hasProfile = true;
            }, function (response) {
                $scope.hasProfile = false;
            })
        };

        $scope.loadProfile();

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:prlEmpGeneratedSalInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('prlEmpGeneratedSalInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            $scope.prlEmpGeneratedSalInfo.updateBy = $scope.loggedInUser.id;
            $scope.prlEmpGeneratedSalInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.prlEmpGeneratedSalInfo.id != null) {
                PrlEmpGeneratedSalInfo.update($scope.prlEmpGeneratedSalInfo, onSaveSuccess, onSaveError);
            } else {
                $scope.prlEmpGeneratedSalInfo.createBy = $scope.loggedInUser.id;
                $scope.prlEmpGeneratedSalInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PrlEmpGeneratedSalInfo.save($scope.prlEmpGeneratedSalInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.isCurrentMonthSelected = false;
        $scope.checkMonth = function()
        {
            var curDate = new Date();
            var curMonth    = $filter('date')(curDate, 'MMM');
            var curYear     = $filter('date')(curDate,'yyyy');
            if(curMonth.toUpperCase() === $scope.prlEmpGeneratedSalInfo.month.toUpperCase() )
            {
                if(curYear == $scope.prlEmpGeneratedSalInfo.yearName)
                {
                    $scope.isCurrentMonthSelected = true;
                }
            }
            else
            {
                $scope.isCurrentMonthSelected = false;
            }
            console.log("CurrentMon: "+curMonth.toUpperCase()+", SelectedMonth: "+$scope.prlEmpGeneratedSalInfo.month+", CurrentYear: "+curYear.toUpperCase()+", SelectedYear: "+$scope.prlEmpGeneratedSalInfo.yearName+", isCurrentMonth: "+$scope.isCurrentMonthSelected);
        };

        $scope.clear = function() {

        };
}]);
