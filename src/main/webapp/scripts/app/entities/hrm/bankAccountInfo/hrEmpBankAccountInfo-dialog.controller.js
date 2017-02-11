'use strict';

angular.module('stepApp').controller('HrEmpBankAccountInfoDialogController',
    ['$scope', '$rootScope', '$stateParams','$state', 'entity', 'HrEmpBankAccountInfo', 'HrEmployeeInfo', 'MiscTypeSetupByCategory','User','Principal','DateUtils','HrEmployeeInfoByWorkArea','HrEmpBankSalaryAccountInfo',
        function($scope, $rootScope, $stateParams, $state, entity, HrEmpBankAccountInfo, HrEmployeeInfo, MiscTypeSetupByCategory, User, Principal, DateUtils,HrEmployeeInfoByWorkArea,HrEmpBankSalaryAccountInfo) {

        $scope.hrEmpBankAccountInfo = entity;
        $scope.hremployeeinfos  = [];//HrEmployeeInfo.query();
        $scope.misctypesetups   = MiscTypeSetupByCategory.get({cat:'BankName',stat:'true'});
        $scope.workAreaList     = MiscTypeSetupByCategory.get({cat:'EmployeeWorkArea',stat:'true'});
        $scope.load = function(id) {
            HrEmpBankAccountInfo.get({id : id}, function(result) {
                $scope.hrEmpBankAccountInfo = result;
            });
        };

        $scope.loadEmployeeByWorkArea = function(workArea)
        {
            //console.log(JSON.stringify(workArea));
            HrEmployeeInfoByWorkArea.get({areaid : workArea.id}, function(result) {
                $scope.hremployeeinfos = result;
                console.log("Total record: "+result.length);
            });
        };

        $scope.salaryAccountCounter = 0;
        $scope.loadSalaryAccount= function (emplInfo)
        {
            //console.log(JSON.stringify(emplInfo.id))
            HrEmpBankSalaryAccountInfo.query({emplId : emplInfo.id}, function(result)
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

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrEmpBankAccountInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrEmpBankAccountInfo');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrEmpBankAccountInfo.updateBy = $scope.loggedInUser.id;
            $scope.hrEmpBankAccountInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.hrEmpBankAccountInfo.id != null)
            {
                $scope.hrEmpBankAccountInfo.logId = 0;
                $scope.hrEmpBankAccountInfo.logStatus = 6;
                HrEmpBankAccountInfo.update($scope.hrEmpBankAccountInfo, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrEmpBankAccountInfo.updated');
            } else
            {
                $scope.hrEmpBankAccountInfo.logId = 0;
                $scope.hrEmpBankAccountInfo.logStatus = 6;
                $scope.hrEmpBankAccountInfo.createBy = $scope.loggedInUser.id;
                $scope.hrEmpBankAccountInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrEmpBankAccountInfo.save($scope.hrEmpBankAccountInfo, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrEmpBankAccountInfo.created');
            }
        };

        $scope.clear = function() {

        };
}]);
