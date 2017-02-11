'use strict';

angular.module('stepApp').controller('InstFinancialInfoDialogController',
    ['$scope', 'BankSetup', '$rootScope', '$stateParams', '$state', 'entity', 'InstFinancialInfo','$q','Division','DistrictsByDivision','UpazilasByDistrict','BankBranchsByUpazilaAndBank','InstFinancialInfoOne','$timeout',
        function($scope, BankSetup, $rootScope, $stateParams,$state, entity, InstFinancialInfo, $q, Division, DistrictsByDivision, UpazilasByDistrict, BankBranchsByUpazilaAndBank, InstFinancialInfoOne, $timeout) {

        $scope.instFinancialInfo = entity;
        $scope.bankSetups = BankSetup.query();
        $scope.divisions = Division.query();
        //$scope.division = {};
        $scope.districts = [];
        //$scope.district = {};
        $scope.upazilas = [];
        //$scope.upazila = {};
        $scope.bankBranchs = [];
        $scope.hideAddMore = true;
        $scope.instFinancialInfos = entity;
        $scope.updatedDistrict = function (division) {
            DistrictsByDivision.query({page: $scope.page, size: 100, id : division.id}, function (result) {
                $scope.districts = result;
                $scope.upazilas = [];
                $scope.bankBranchs =[];
            });

        };
        $scope.updatedUpazila = function (district) {
            UpazilasByDistrict.query({page: $scope.page, size: 100, id : district.id}, function (result) {
                $scope.upazilas = result;
                $scope.bankBranchs = [];
            });

        };
        $scope.updatedBankBranch = function (upazila, bank) {
            BankBranchsByUpazilaAndBank.query({page: $scope.page, size: 100, upazilaId : upazila.id, bankSetupId: bank.id}, function (result) {
                $scope.bankBranchs = result;
                console.log('Hi');
                console.log($scope.bankBranchs);
            });

        };



            InstFinancialInfoOne.get({id: $stateParams.id}, function(result){
            console.log(result);
                $scope.instFinancialInfos = [1];
            $scope.instFinancialInfos[0] = result;
                $scope.hideAddMore = true;
                $timeout(function() {
                    $rootScope.refreshRequiredFields();
                }, 100);

                /* if(result.bankBranch){
                     $scope.division = result.bankBranch.upazila.district.division;
                     $scope.district = result.bankBranch.upazila.district;
                     $scope.upazila = result.bankBranch.upazila;
                 }*/

        });
        $scope.incrementInstGenINfoStatus = true;
            console.log($scope.instFinancialInfos);
        $scope.load = function(id) {
            InstFinancialInfo.get({id : id}, function(result) {
                $scope.instFinancialInfos = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instFinancialInfoUpdate', result);
            console.log(result);
            $rootScope.setWarningMessage('stepApp.instFinancialInfo.updated');
            $scope.isSaving = false;
            $state.go('instituteInfo.financialInfo',{},{reload:true});


        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.addMore = function(){
            $scope.instFinancialInfos.push(
                {
                    branchName: null,
                    accountType: null,
                    accountNo: null,
                    issueDate: null,
                    expireDate: null,
                    amount: null,
                    status: null,
                    id:null
                }
            );
            $rootScope.refreshRequiredFields();
            console.log($scope.instFinancialInfos);
        }
        $scope.removeBankInfo = function(bankInfo){
            var index =  $scope.instFinancialInfos.indexOf(bankInfo);
            $scope.instFinancialInfos.splice(index,1);

        }


            $scope.deadlineValidation = function (issueDate,expireDate) {
                console.log("come to date vallidation");

                var d1 = Date.parse(expireDate);
                var d2 = Date.parse(issueDate);
                console.log(expireDate);
                console.log('------------');
                console.log(issueDate);

                if (d1 <= d2) {

                    console.log("d1 less than d2");
                    $scope.dateError = true;
                }else {
                    console.log("d1 greater than d2");

                    $scope.dateError = false;
                }
            };
        $scope.save = function () {
            $scope.isSaving = true;
            console.log('comes to save method');
            console.log($stateParams.id);
            $scope.instFinancialInfos.forEach(function (data, i) {
                console.log(data);
                if(data.id !=null){
                    $scope.incrementInstGenINfoStatus
                     data.status=0;
                     console.log('comes to save add more 1');
                     console.log(data);
                     InstFinancialInfo.update(data, onSaveSuccess, onSaveError);
                    if(data.status==1){
                        if($scope.incrementInstGenINfoStatus){
                            console.log('  after approved and first time save');
                            data.status=3
                            $scope.incrementInstGenINfoStatus=false;
                            InstFinancialInfo.update(data, onSaveSuccess, onSaveError);
                            $rootScope.setWarningMessage('stepApp.instFinancialInfo.updated');
                            console.log('comes to save status 1');
                        }else{
                            console.log('  after approved and 2nd time save');
                            data.status=0
                            InstFinancialInfo.update(data, onSaveSuccess, onSaveError);
                            //InstFinancialInfo.update(data);
                            console.log('comes to save else status 1');
                            $rootScope.setWarningMessage('stepApp.instFinancialInfo.updated');
                        }
                    }else{
                        data.status=0;
                        console.log('  before approved');
                        InstFinancialInfo.update(data, onSaveSuccess, onSaveError);
                        $rootScope.setSuccessMessage('stepApp.instFinancialInfo.updated');
                        //InstFinancialInfo.update(data);
                    }
                }else{
                    console.log('comes to main save =====================');
                    InstFinancialInfo.save(data, onSaveSuccess, onSaveError);
                    console.log('comes to main save =====================');
                    data.status=0;
                    console.log(data);
                    $rootScope.setSuccessMessage('stepApp.instFinancialInfo.created');
/*
                    if( data.branchName != null && data.bankName != null && data.accountType != null && data.accountNo != null &&data.amount != null){
                        //InstFinancialInfo.save(data);
                    }
*/
                }
            });
            /*$q.all(request).then(function () {
                $state.go('instituteInfo.financialInfo',{},{reload:true});
            });*/
        };
        $scope.clear = function() {
           $scope.instFinancialInfo = null;
        };
}]);
