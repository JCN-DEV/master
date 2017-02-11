'use strict';

angular.module('stepApp').controller('TrainingCategorySetupDialogController',
    ['$scope', '$state', '$rootScope', '$stateParams', 'entity', 'TrainingCategorySetup',
        function($scope, $state, $rootScope, $stateParams, entity, TrainingCategorySetup) {

        $scope.trainingCategorySetup = entity;

        $scope.load = function(id) {
            TrainingCategorySetup.get({id : id}, function(result) {
                $scope.trainingCategorySetup = result;
            });
        };


        var onSaveFinished = function (result) {
            $state.go('trainingInfo.trainingCategorySetup', null, { reload: true });
            $scope.$emit('stepApp:trainingCategorySetupUpdate', result);

        };

        $scope.save = function () {
            if ($scope.trainingCategorySetup.id != null) {
                TrainingCategorySetup.update($scope.trainingCategorySetup, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.trainingCategorySetup.updated');
            } else {
                if($scope.trainingCategorySetup.status == null){
                    $scope.trainingCategorySetup.status = true;
                }
                    TrainingCategorySetup.save($scope.trainingCategorySetup, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.trainingCategorySetup.created');
            }
        };

        $scope.clear = function() {
            $state.go('trainingInfo.trainingCategorySetup', null, { reload: true });
        };
}]);
