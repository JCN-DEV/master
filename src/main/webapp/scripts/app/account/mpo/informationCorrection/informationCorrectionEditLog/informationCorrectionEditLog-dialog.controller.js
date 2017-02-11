'use strict';

angular.module('stepApp').controller('InformationCorrectionEditLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InformationCorrectionEditLog', 'InformationCorrection',
        function($scope, $stateParams, $modalInstance, entity, InformationCorrectionEditLog, InformationCorrection) {

        $scope.informationCorrectionEditLog = entity;
        $scope.informationcorrections = InformationCorrection.query();
        $scope.load = function(id) {
            InformationCorrectionEditLog.get({id : id}, function(result) {
                $scope.informationCorrectionEditLog = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:informationCorrectionEditLogUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.informationCorrectionEditLog.id != null) {
                InformationCorrectionEditLog.update($scope.informationCorrectionEditLog, onSaveSuccess, onSaveError);
            } else {
                InformationCorrectionEditLog.save($scope.informationCorrectionEditLog, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
