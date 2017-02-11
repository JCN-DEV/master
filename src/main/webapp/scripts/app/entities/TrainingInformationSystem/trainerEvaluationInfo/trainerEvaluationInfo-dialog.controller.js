'use strict';

angular.module('stepApp').controller('TrainerEvaluationInfoDialogController',
    ['$scope', '$state','$stateParams', '$rootScope', 'entity', 'TrainerEvaluationInfo', 'TrainingHeadSetup', 'TrainingInitializationInfo', 'TraineeInformation','TrainerListByTrainingCode','TrainingInitializeDataByTrainingCode',
        function($scope,$state ,$stateParams, $rootScope, entity, TrainerEvaluationInfo, TrainingHeadSetup, TrainingInitializationInfo, TraineeInformation,TrainerListByTrainingCode,TrainingInitializeDataByTrainingCode) {

        $scope.trainerEvaluationInfo = entity;
        $scope.trainingheadsetups = TrainingHeadSetup.query();
        $scope.traininginitializationinfos = TrainingInitializationInfo.query();
        $scope.traineeinformations = TraineeInformation.query();
        $scope.trainerLists = [];
        $scope.trainerEvaluationInfo2 = {};
        $scope.load = function(id) {
            TrainerEvaluationInfo.get({id : id}, function(result) {
                $scope.trainerEvaluationInfo = result;
            });
        };

        $scope.TrainerListByTrainingCode = function (trainingCode) {
            TrainerListByTrainingCode.query({pTrainingCode : trainingCode}, function(result) {
                $scope.trainerLists = result;
            });
            TrainingInitializeDataByTrainingCode.query({pTrainingCode : trainingCode},function(rest){
                $scope.trainerEvaluationInfo.trainingInitializationInfo = rest;
            });
        };

        var onSaveFinished = function (result) {
            $state.go('trainingInfo.trainerEvaluationInfo', null, { reload: true });
            $scope.$emit('stepApp:trainerEvaluationInfoUpdate', result);
        };

        $scope.save = function () {
            //if ($scope.trainerEvaluationInfo.id != null) {
            //    TrainerEvaluationInfo.update($scope.trainerEvaluationInfo, onSaveFinished);
            //} else {
                angular.forEach($scope.trainerLists,function(trainer){
                    $scope.trainerEvaluationInfo2.trainerInformation = trainer;
                    $scope.trainerEvaluationInfo2.performance = trainer.performance;
                    $scope.trainerEvaluationInfo2.remarks = trainer.remarks;
                    $scope.trainerEvaluationInfo2.evaluationDate = $scope.trainerEvaluationInfo.evaluationDate;
                    $scope.trainerEvaluationInfo2.trainingInitializationInfo = $scope.trainerEvaluationInfo.trainingInitializationInfo;
                    $scope.trainerEvaluationInfo2.remarks = trainer.remarks;
                    TrainerEvaluationInfo.save($scope.trainerEvaluationInfo2);
                });
            $rootScope.setSuccessMessage('stepApp.trainerEvaluationInfo.created');
            $state.go('trainingInfo.trainerEvaluationInfo', null, { reload: true });
            $scope.$emit('stepApp:trainerEvaluationInfoUpdate', result);

            //}
        };

        $scope.clear = function() {
           //  $modalInstance.dismiss('cancel');
        };

        if ($stateParams.id) {
            $scope.load($stateParams.id);
        }
        else {
            $scope.trainerLists.push({
                performance: '',
                remarks: ''
            });
        }
}]);
