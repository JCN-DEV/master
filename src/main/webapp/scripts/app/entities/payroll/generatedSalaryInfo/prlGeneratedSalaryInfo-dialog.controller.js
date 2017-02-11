'use strict';

angular.module('stepApp').controller('PrlGeneratedSalaryInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'PrlGeneratedSalaryInfo','User','Principal','DateUtils','PrlSalaryGenerationProc','PrlSalaryDisburseProc','PrlGeneratedSalaryInfoFilter','PrlGeneratedSalaryInfoHistory',
        function($scope, $rootScope, $stateParams, $state, entity, PrlGeneratedSalaryInfo, User, Principal, DateUtils, PrlSalaryGenerationProc,PrlSalaryDisburseProc,PrlGeneratedSalaryInfoFilter,PrlGeneratedSalaryInfoHistory) {

        $scope.prlGeneratedSalaryInfo = entity;
        $scope.load = function(id) {
            PrlGeneratedSalaryInfo.get({id : id}, function(result) {
                $scope.prlGeneratedSalaryInfo = result;
            });
        };

        $scope.prlGeneratedSalaryInfoHist = {};

        $scope.alreadyGenerated = false;
        $scope.isGenerate = true;
        $scope.alreadyDisbursed = false;
        $scope.allDataSelected = false;

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

        $scope.checkAlreadyGeneratedSalary = function ()
        {
            $scope.allDataSelected = false;
            console.log("Check old data month: "+$scope.prlGeneratedSalaryInfo.monthName+", year: "+$scope.prlGeneratedSalaryInfo.yearName+", type: "+$scope.prlGeneratedSalaryInfo.salaryType );
            if($scope.prlGeneratedSalaryInfo.salaryType != null &&
                $scope.prlGeneratedSalaryInfo.monthName != null &&
                $scope.prlGeneratedSalaryInfo.yearName != null
            )
            {
                $scope.allDataSelected = true;
                PrlGeneratedSalaryInfoFilter.get({year: $scope.prlGeneratedSalaryInfo.yearName,
                    month: $scope.prlGeneratedSalaryInfo.monthName,
                    saltype:$scope.prlGeneratedSalaryInfo.salaryType}, function (result)
                {
                    console.log("resultOnMontyYearType check:"+result+",json:  "+JSON.stringify(result));
                    //$scope.generatedSalaryDto = resultDto;

                    if(result!=null && result.length > 0)
                    {
                        $scope.alreadyGenerated = true;
                        if(result[0].disburseStatus==='Y')
                            $scope.alreadyDisbursed = true;
                        else $scope.alreadyDisbursed = false;
                    }
                    else
                    {
                        $scope.alreadyGenerated = false;
                        $scope.alreadyDisbursed = false;
                    }

                    //console.log("PST: AllowanceExtraLen: "+$scope.allowanceExtraList.length+", DeductionExtraLen: "+$scope.deductionExtraList.length);
                    $scope.isLoading = false;
                }, function (response)
                {
                    console.log("error: "+response);
                });
            }

        };

        $scope.getLastInsertedDataHistory = function ()
        {
            console.log("data inserted history " );

            PrlGeneratedSalaryInfoHistory.get({}, function (result)
            {
                console.log("GeneratedSalaryDto: "+JSON.stringify(result));
                $scope.prlGeneratedSalaryInfoHist = result;
                $scope.isLoading = false;
            }, function (response)
            {
                console.log("error: "+response);
            });

        };

        $scope.getLastInsertedDataHistory();

        $scope.yearList = $rootScope.generateYearList(new Date().getFullYear()-1, 5);

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:prlGeneratedSalaryInfoUpdate', result);
            $scope.isSaving = false;
            $scope.responseMsg = result;
            //$state.go('prlGeneratedSalaryInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
            $scope.responseMsg = result;
        };

        $scope.generateSalary = function () {
            $scope.isSaving = true;
            $scope.prlGeneratedSalaryInfo.updateBy = $scope.loggedInUser.id;
            $scope.prlGeneratedSalaryInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.prlGeneratedSalaryInfo.id != null) {
                PrlGeneratedSalaryInfo.update($scope.prlGeneratedSalaryInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.prlGeneratedSalaryInfo.updated');
            } else {
                $scope.prlGeneratedSalaryInfo.createBy = $scope.loggedInUser.id;
                $scope.prlGeneratedSalaryInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PrlGeneratedSalaryInfo.save($scope.prlGeneratedSalaryInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.prlGeneratedSalaryInfo.created');
            }
        };

        $scope.disburseSalary = function ()
        {
            $scope.isSaving = true;
            $scope.prlGeneratedSalaryInfo.updateBy = $scope.loggedInUser.id;
            $scope.prlGeneratedSalaryInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            $scope.prlGeneratedSalaryInfo.processDate = DateUtils.convertLocaleDateToServer(new Date());
            $scope.prlGeneratedSalaryInfo.createBy = $scope.loggedInUser.id;
            $scope.prlGeneratedSalaryInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
            console.log(JSON.stringify( $scope.prlGeneratedSalaryInfo));
            PrlSalaryDisburseProc.update($scope.prlGeneratedSalaryInfo, onSaveDisburseSuccess, onSaveDisburseError);
            $rootScope.setWarningMessage('stepApp.prlGeneratedSalaryInfo.updated');
        };

        var onSaveDisburseSuccess = function (result) {
            $scope.$emit('stepApp:prlGeneratedSalaryInfoUpdate', result);
            $scope.isSaving = false;
            $scope.responseMsg = result;
            //$state.go('prlGeneratedSalaryInfo');
        };

        var onSaveDisburseError = function (result) {
            $scope.isSaving = false;
            $scope.responseMsg = result;
        };

        $scope.clear = function() {

        };
}]);
