'use strict';

angular.module('stepApp').controller('DlBookEditionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'DlBookEdition', 'DlBookInfo',
        function($scope, $stateParams, $modalInstance, entity, DlBookEdition, DlBookInfo) {

        $scope.dlBookEdition = entity;
        $scope.dlbookinfos = DlBookInfo.query();
        $scope.load = function(id) {
            DlBookEdition.get({id : id}, function(result) {
                $scope.dlBookEdition = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:dlBookEditionUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dlBookEdition.id != null) {
                DlBookEdition.update($scope.dlBookEdition, onSaveSuccess, onSaveError);
            } else {
                DlBookEdition.save($scope.dlBookEdition, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
