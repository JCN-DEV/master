'use strict';

angular.module('stepApp').controller('TraineeEvaluationInfoDialogController',
    ['$scope', '$state', '$stateParams', 'entity', 'TraineeEvaluationInfo', 'TrainingInitializationInfo','TraineeListByTrainingCode','TrainingInitializeDataByTrainingCode',
        function($scope, $state, $stateParams, entity, TraineeEvaluationInfo, TrainingInitializationInfo,TraineeListByTrainingCode,TrainingInitializeDataByTrainingCode) {

        $scope.traineeEvaluationInfo = entity;
        $scope.traineeEvaluationInfoList = [];
        $scope.traineeLists = [];
        $scope.traineeEvaluationInfo2 = {};
        $scope.traineeEvaluationInfo2.trainingInitializationInfo = {};
       //  $scope.trainingheadsetups = TrainingHeadSetup.query();
        // $scope.hremployeeinfos = HrEmployeeInfo.query();
        // $scope.traininginitializationinfos = TrainingInitializationInfo.query();
        $scope.load = function(id) {
            TraineeEvaluationInfo.get({id : id}, function(result) {
                $scope.traineeEvaluationInfo = result;
            });
        };

        $scope.TraineeListByTrainingCode = function (trainingCode) {
           //  $scope.headSetup = headSetup;
            TraineeListByTrainingCode.query({pTrainingCode : trainingCode}, function(result) {
                $scope.traineeLists = result;
            });
            TrainingInitializeDataByTrainingCode.query({pTrainingCode : trainingCode},function(rest){
                // $scope.trainingInitializationInfo = res;
                console.log(rest);
                $scope.traineeEvaluationInfo2.trainingInitializationInfo = rest;
            });
        };

        var onSaveFinished = function (result) {
            $state.go('trainingInfo.traineeEvaluationInfo', null, { reload: true });
            $scope.$emit('stepApp:traineeEvaluationInfoUpdate', result);
        };

        $scope.save = function () {
                angular.forEach($scope.traineeLists, function (traineeList) {
                    $scope.traineeEvaluationInfo2.id = "";
                    $scope.traineeEvaluationInfo2.traineeInformation = traineeList;
                    $scope.traineeEvaluationInfo2.performance = traineeList.performance;
                    $scope.traineeEvaluationInfo2.mark = traineeList.mark;
                    $scope.traineeEvaluationInfo2.remarks = traineeList.remarks;
                    $scope.traineeEvaluationInfo2.evaluationDate = $scope.traineeEvaluationInfo.evaluationDate;
                    console.log($scope.traineeEvaluationInfo2);
                    TraineeEvaluationInfo.save($scope.traineeEvaluationInfo2);
                });
            $rootScope.setSuccessMessage('stepApp.traineeEvaluationInfo.created');
            $state.go('trainingInfo.traineeEvaluationInfo', null, { reload: true });
        };

        if ($stateParams.id) {
            $scope.load($stateParams.id);
        }
        else {
            $scope.traineeLists.push({
                performance: '',
                mark: '',
                remarks: ''
            });
        }

        $scope.clear = function() {
            $state.go('trainingInfo.traineeEvaluationInfo', null, { reload: true });
        };
}]);
