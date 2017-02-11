'use strict';

angular.module('stepApp').controller('TrainingSummaryDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'TrainingSummary', 'User',
        function($scope, $stateParams, $modalInstance, entity, TrainingSummary, User) {

        $scope.trainingSummary = entity;
        $scope.users = User.query({size: 500});
        $scope.load = function(id) {
            TrainingSummary.get({id : id}, function(result) {
                $scope.trainingSummary = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:trainingSummaryUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.trainingSummary.id != null) {
                TrainingSummary.update($scope.trainingSummary, onSaveFinished);
            } else {
                TrainingSummary.save($scope.trainingSummary, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
