'use strict';

angular.module('stepApp').controller('DlContCatSetDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'DlContCatSet', 'DlContTypeSet',
        function($scope, $stateParams, $modalInstance, entity, DlContCatSet, DlContTypeSet) {

        $scope.dlContCatSet = entity;
        $scope.dlconttypesets = DlContTypeSet.query();
        $scope.load = function(id) {
            DlContCatSet.get({id : id}, function(result) {
                $scope.dlContCatSet = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:dlContCatSetUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dlContCatSet.id != null) {
                DlContCatSet.update($scope.dlContCatSet, onSaveSuccess, onSaveError);
            } else {
                DlContCatSet.save($scope.dlContCatSet, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
