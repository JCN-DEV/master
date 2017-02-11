'use strict';

angular.module('stepApp')
    .controller('TrainingRequisitionApproveAndForwardDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'TrainingRequisitionApproveAndForward', 'TrainingRequisitionForm','Principal',
        function($scope, $stateParams, $modalInstance, entity, TrainingRequisitionApproveAndForward, TrainingRequisitionForm,Principal) {

        $scope.trainingRequisitionApproveAndForward = entity;
        $scope.trainingrequisitionforms = TrainingRequisitionForm.query();
        $scope.trainingRequisitionApproveAndForward.forwardToAuthority = "DG";
        $scope.forwardAuthority = true;

        $scope.load = function(id) {
            TrainingRequisitionApproveAndForward.get({id : id}, function(result) {
                $scope.trainingRequisitionApproveAndForward = result;
            });
        };

        if(Principal.hasAnyAuthority(['ROLE_DG'])){
            $scope.trainingRequisitionApproveAndForward.forwardToAuthority = "User";
            $scope.forwardAuthority = false;
        }

        TrainingRequisitionForm.get({id:$stateParams.trainingReqId},function(result){
            $scope.trainingRequisitionApproveAndForward.trainingRequisitionForm = result;
        });

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:trainingRequisitionApproveAndForwardUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.trainingRequisitionApproveAndForward.id != null) {
                TrainingRequisitionApproveAndForward.update($scope.trainingRequisitionApproveAndForward, onSaveFinished);
            } else {
                TrainingRequisitionApproveAndForward.save($scope.trainingRequisitionApproveAndForward, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
    }])
    .controller('TrainingRequisitionDeclineController',
        ['$scope','$stateParams' ,'$modalInstance', 'TrainingRequisitionApproveAndForward', '$state', 'entity', 'TrainingRequisitionForm','TrainingRequisitionDecline',
            function($scope,$stateParams ,$modalInstance, TrainingRequisitionApproveAndForward, $state, entity, TrainingRequisitionForm,TrainingRequisitionDecline) {

            $scope.decline = function(){
                TrainingRequisitionForm.get({id:$stateParams.trainingReqId},function(result){
                    $scope.trainingRequisitionApproveAndForward.trainingRequisitionForm = result;
                    $scope.trainingRequisitionApproveAndForward.comments = "Training Requisition Decline";
                    TrainingRequisitionDecline.save($scope.trainingRequisitionApproveAndForward,onSaveFinished);
                });
            };

            var onSaveFinished = function (result) {
                $modalInstance.close();
                $state.go('trainingInfo.reqPendingList',{},{reload:true});
            };

            $scope.clear = function() {
                $modalInstance.close();
            };
    }])
    .controller('TrainingRequisitionRejectController',
        ['$scope','$state','$stateParams','$modalInstance', 'TrainingRequisitionApproveAndForward', 'entity', 'TrainingRequisitionReject','TrainingRequisitionForm',
        function($scope,$state,$stateParams, $modalInstance, TrainingRequisitionApproveAndForward, entity, TrainingRequisitionReject,TrainingRequisitionForm) {
            $scope.trainingRequisitionApproveAndForward = entity;

            var onSaveSuccess = function (result) {
                $modalInstance.close(result);
                $scope.isSaving = false;
                $state.go('trainingInfo.reqPendingList',{},{reload:true});
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };

            $scope.clear = function() {
                $modalInstance.close();
            };
            $scope.confirmReject = function () {
                $scope.isSaving = true;
                console.log('Reject-- '+$stateParams.reqID);
                TrainingRequisitionForm.get({id:$stateParams.reqID},function(result){

                    $scope.trainingRequisitionApproveAndForward.trainingRequisitionForm = result;
                    TrainingRequisitionReject.save($scope.trainingRequisitionApproveAndForward, onSaveSuccess, onSaveError);
                });
            };
    }]);
