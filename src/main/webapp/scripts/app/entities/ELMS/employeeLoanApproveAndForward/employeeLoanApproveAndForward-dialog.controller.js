'use strict';

angular.module('stepApp').controller('EmployeeLoanApproveAndForwardDialogController',
    ['$scope', '$stateParams','$modalInstance','$state' ,'entity', 'EmployeeLoanApproveAndForward', 'EmployeeLoanRequisitionForm','Authority','Principal','GetForwardMessageByReqIdAndApproveStatus',
        'GetEmployeeLoanVocApprovalRole','GetEmployeeLoanOthersApprovalRole','GetEmployeeLoanDteApprovalRole',
        function($scope, $stateParams,$modalInstance,$state ,entity, EmployeeLoanApproveAndForward, EmployeeLoanRequisitionForm,Authority,Principal,GetForwardMessageByReqIdAndApproveStatus,
                 GetEmployeeLoanVocApprovalRole,GetEmployeeLoanOthersApprovalRole,GetEmployeeLoanDteApprovalRole) {

        $scope.employeeLoanApproveAndForward = {};
        $scope.authorityHide = false;
        $scope.approveLoanAmountDiv = false;
        $scope.approveLoanInstallmentDiv = false;
        $scope.authoritys = [];
        //$scope.employeeloanrequisitionforms = EmployeeLoanRequisitionForm.query();

        //$scope.authoritys = [{name:'ROLE_INSTITUTE'},{name:'ROLE_AD'},{name:'ROLE_DIRECTOR'},{name:'ROLE_DG'}];

        EmployeeLoanRequisitionForm.get({id:$stateParams.loanReqId},function(result){

            //GetForwardMessageByReqIdAndApproveStatus.get({approveStatus : result.approveStatus},function(result){
            //    $scope.employeeLoanApproveAndForward.approveFrom = result.prev ;
            //    $scope.employeeLoanApproveAndForward.forwardToAuthority = result.next;
            //});
            $scope.employeeLoanApproveAndForward.loanRequisitionForm = result;
            console.log('--------'+$scope.employeeLoanApproveAndForward.loanRequisitionForm.applyType);
            if($scope.employeeLoanApproveAndForward.loanRequisitionForm.applyType == "VOC"){
                GetEmployeeLoanVocApprovalRole.query(function(result){
                    $scope.authoritys = result;
                    console.log($scope.authoritys);
                });
            }else if($scope.employeeLoanApproveAndForward.loanRequisitionForm.applyType == "Others"){
                GetEmployeeLoanOthersApprovalRole.query(function(result){
                    $scope.authoritys = result;
                    console.log($scope.authoritys);
                });
            }else if($scope.employeeLoanApproveAndForward.loanRequisitionForm.applyType == "DTE"){
                GetEmployeeLoanDteApprovalRole.query(function(result){
                    $scope.authoritys = result;
                });
            }else {
                $scope.authoritys = null;
            }
        });

        //if(Principal.hasAnyAuthority(['ROLE_INSTITUTE'])){
        //    $scope.authoritys.splice(0,1);
        //}else if(Principal.hasAnyAuthority(['ROLE_AD'])){
        //    $scope.authoritys.splice(1,1);
        //}else if(Principal.hasAnyAuthority(['ROLE_DIRECTOR'])){
        //    $scope.authoritys.splice(2,1);
        //}else if(Principal.hasAnyAuthority(['ROLE_DG'])){
        //    $scope.authoritys.push([{name:null}]);
        //    $scope.authorityHide = true;
        //    $scope.approveLoanAmountDiv = true;
        //    $scope.approveLoanInstallmentDiv = true;
        //    $scope.authoritys.push([{name:'ROLE_USER'}]);
        //}

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:employeeLoanApproveAndForwardUpdate', result);
            $modalInstance.close();
            $state.go('employeeLoanInfo.employeeLoanReqPending',{},{reload:true});
        };

        $scope.save = function () {
            EmployeeLoanRequisitionForm.get({id:$stateParams.loanReqId},function(result){
                $scope.employeeLoanApproveAndForward.loanRequisitionForm = result;
                console.log('+++++++++++++++++');
                console.log($scope.employeeLoanApproveAndForward.forwardToAuthority.role);
                $scope.employeeLoanApproveAndForward.forwardToAuthority = $scope.employeeLoanApproveAndForward.forwardToAuthority.role;
                EmployeeLoanApproveAndForward.save($scope.employeeLoanApproveAndForward, onSaveFinished);
            });
        };

        $scope.clear = function() {
            $modalInstance.close();
            $state.go('employeeLoanInfo.employeeLoanReqPending',{},{reload:true});
        };

    }])
    .controller('EmployeeLoanDeclineController',
        ['$scope','$stateParams' ,'$modalInstance', 'EmployeeLoanApproveAndForward', '$state', 'entity', 'EmployeeLoanRequisitionForm','EmployeeLoanDecline',
        function($scope,$stateParams ,$modalInstance, EmployeeLoanApproveAndForward, $state, entity, EmployeeLoanRequisitionForm,EmployeeLoanDecline) {

            $scope.decline = function(){
                EmployeeLoanRequisitionForm.get({id:$stateParams.loanReqId},function(result){
                    $scope.employeeLoanApproveAndForward.loanRequisitionForm = result;
                    $scope.employeeLoanApproveAndForward.comments = "Loan Application Decline";
                    EmployeeLoanDecline.save($scope.employeeLoanApproveAndForward,onSaveFinished);
                });
            };

            var onSaveFinished = function (result) {
                // $scope.$emit('stepApp:employeeLoanApproveAndForwardUpdate', result);
                $modalInstance.close();
                $state.go('employeeLoanInfo.employeeLoanReqPending',{},{reload:true});
            };

            $scope.clear = function() {
                $modalInstance.close();
            };
    }]);
