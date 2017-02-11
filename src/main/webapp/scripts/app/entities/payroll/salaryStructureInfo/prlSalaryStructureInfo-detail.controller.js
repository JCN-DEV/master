'use strict';

angular.module('stepApp')
    .controller('PrlSalaryStructureInfoDetailController', function ($scope, $rootScope, $stateParams, $timeout, entity, PrlSalaryStructureInfo, PrlPayscaleInfo, PrlPayscaleBasicInfo, HrEmployeeInfo, PrlSalaryStructureInfoByFilter) {
        $scope.prlSalaryStructureInfo = entity;
        $scope.load = function (id) {
            PrlSalaryStructureInfo.get({id: id}, function(result) {
                $scope.prlSalaryStructureInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:prlSalaryStructureInfoUpdate', function(event, result) {
            $scope.prlSalaryStructureInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.loadAllowanceDeductionList = function()
        {
            //console.log("loadAllowanceList PSID: "+$scope.prlPayscaleInfo.id);
            if($scope.prlSalaryStructureInfo.id != null)
            {
                console.log("EditMode: PsID: "+$scope.prlSalaryStructureInfo.payscaleInfo.id+", GrdID: "+$scope.prlSalaryStructureInfo.employeeInfo.designationInfo.gradeInfo.id+", EmpId: "+$scope.prlSalaryStructureInfo.employeeInfo.id);
                PrlSalaryStructureInfoByFilter.query({psid: $scope.prlSalaryStructureInfo.payscaleInfo.id,
                    grdid: $scope.prlSalaryStructureInfo.employeeInfo.designationInfo.gradeInfo.id,
                    empid: $scope.prlSalaryStructureInfo.employeeInfo.id}, function (result)
                {
                    console.log("loadAllowanceDeductionList: "+JSON.stringify(result));
                    $scope.prlSalAllowDeductList = result;

                }, function (response)
                {
                    console.log("error: "+response);
                });
            }
        };

        $timeout(function()
        {
            console.log("Loading allowance deduction list: ");
            $scope.loadAllowanceDeductionList();
        }, 800);

    });
