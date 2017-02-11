'use strict';

angular.module('stepApp')
	.controller('EmployeeLoanRejectDialogController',
    ['$scope', '$modalInstance', 'EmployeeLoanApproveAndForward', '$state', 'entity', 'EmployeeLoanReject','$stateParams','EmployeeLoanRequisitionForm',
    function($scope, $modalInstance, EmployeeLoanApproveAndForward, $state, entity, EmployeeLoanReject,$stateParams,EmployeeLoanRequisitionForm) {
            $scope.employeeLoanApproveAndForward = entity;


        var onSaveSuccess = function (result) {
            //$scope.$emit('stepApp:instituteUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('employeeLoanInfo.employeeLoanReqPending',{},{reload:true});
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.clear = function() {
            $modalInstance.close();
        };
        $scope.confirmReject = function () {
            $scope.isSaving = true;
            console.log('Reject-- '+$stateParams.id);
            EmployeeLoanRequisitionForm.get({id:$stateParams.id},function(result){
                $scope.employeeLoanApproveAndForward.loanRequisitionForm = result;
                EmployeeLoanReject.save($scope.employeeLoanApproveAndForward, onSaveSuccess, onSaveError);
            });

        };



    }]);
