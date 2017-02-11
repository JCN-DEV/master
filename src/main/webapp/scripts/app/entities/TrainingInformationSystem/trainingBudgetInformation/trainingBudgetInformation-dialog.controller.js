'use strict';

angular.module('stepApp').controller('TrainingBudgetInformationDialogController',
            ['$scope', '$rootScope', '$state', '$stateParams', 'entity', 'TrainingBudgetInformation', 'TrainingInitializationInfo',
            'TrainingInitializeDataByTrainingCode','GetBudgetInfoByInitializationId',
        function($scope, $state, $rootScope, $stateParams, entity, TrainingBudgetInformation, TrainingInitializationInfo,
                 TrainingInitializeDataByTrainingCode,GetBudgetInfoByInitializationId) {

        $scope.trainingBudgetInformation = entity;
        $scope.traininginitializationinfos = TrainingInitializationInfo.query({filter: 'trainingbudgetinformation-is-null'});
        $scope.budgetError = false;
        $scope.budgetInvalid = true;
        $scope.load = function(id) {
            TrainingBudgetInformation.get({id : id}, function(result) {
                $scope.trainingBudgetInformation = result;
            });
        };

        if($stateParams.id != null){
            $scope.budgetInvalid = false;
        }
        var onSaveFinished = function (result) {
            $state.go('trainingInfo.trainingBudgetInformation', null, { reload: true });
            $scope.$emit('stepApp:trainingBudgetInformationUpdate', result);

        };

        $scope.save = function () {
            if ($scope.trainingBudgetInformation.id != null) {
                TrainingBudgetInformation.update($scope.trainingBudgetInformation, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.trainingBudgetInformation.updated');
            } else {
                if($scope.trainingBudgetInformation.status == null){
                    $scope.trainingBudgetInformation.status = true;
                }
                TrainingBudgetInformation.save($scope.trainingBudgetInformation, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.trainingBudgetInformation.created');
            }
        };

        $scope.clear = function() {
            $state.go('trainingInfo.trainingBudgetInformation', null, { reload: true });
        };

        $scope.getTrainingInitializeData = function(){

            TrainingInitializeDataByTrainingCode.get({pTrainingCode:$scope.trainingBudgetInformation.trainingCode},function(result){
                console.log(result.id);
                GetBudgetInfoByInitializationId.get({initializeId :result.id}, function(res) {
                    console.log(res);
                    if(res != null){
                        $scope.budgetError = true;
                        $scope.budgetInvalid = true;
                    }
                },function(response){
                    if(response.status===404){
                        $scope.budgetError = false;
                        $scope.budgetInvalid = false;
                    }
                });

                $scope.trainingBudgetInformation.trainingInitializationInfo = result;
            });
        };
}]);
