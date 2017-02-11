'use strict';

angular.module('stepApp').controller('InformationCorrectionStatusLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'InformationCorrectionStatusLog', 'InformationCorrection',
        function($scope, $stateParams, $modalInstance, entity, InformationCorrectionStatusLog, InformationCorrection) {

        $scope.informationCorrectionStatusLog = entity;
        $scope.informationcorrections = InformationCorrection.query();
        $scope.load = function(id) {
            InformationCorrectionStatusLog.get({id : id}, function(result) {
                $scope.informationCorrectionStatusLog = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:informationCorrectionStatusLogUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.informationCorrectionStatusLog.id != null) {
                InformationCorrectionStatusLog.update($scope.informationCorrectionStatusLog, onSaveSuccess, onSaveError);
            } else {
                InformationCorrectionStatusLog.save($scope.informationCorrectionStatusLog, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
