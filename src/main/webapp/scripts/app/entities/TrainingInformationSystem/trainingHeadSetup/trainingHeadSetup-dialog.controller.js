'use strict';

angular.module('stepApp').controller('TrainingHeadSetupDialogController',
    ['$scope', '$state', '$rootScope', '$stateParams', 'entity', 'TrainingHeadSetup',
        function($scope, $state, $rootScope, $stateParams, entity, TrainingHeadSetup) {

        $scope.trainingHeadSetup = entity;
        $scope.load = function(id) {
            TrainingHeadSetup.get({id : id}, function(result) {
                $scope.trainingHeadSetup = result;
            });
        };

        var onSaveFinished = function (result) {
            $state.go('trainingInfo.trainingHeadSetup', null, { reload: true });
            $scope.$emit('stepApp:trainingHeadSetupUpdate', result);

        };

        $scope.save = function () {
            if ($scope.trainingHeadSetup.id != null) {
                TrainingHeadSetup.update($scope.trainingHeadSetup, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.trainingHeadSetup.updated');
            } else {
                if($scope.trainingHeadSetup.status == null){
                    $scope.trainingHeadSetup.status = true;
                }
                TrainingHeadSetup.save($scope.trainingHeadSetup, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.trainingHeadSetup.created');
            }
        };

        $scope.clear = function() {
        $state.go('trainingInfo.trainingHeadSetup', null, { reload: true });
        };
}]);
