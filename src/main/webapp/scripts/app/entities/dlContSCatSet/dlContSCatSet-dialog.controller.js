'use strict';

angular.module('stepApp').controller('DlContSCatSetDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'DlContSCatSet', 'DlContTypeSet', 'DlContCatSet',
        function($scope, $stateParams, $modalInstance, entity, DlContSCatSet, DlContTypeSet, DlContCatSet) {

        $scope.dlContSCatSet = entity;
        $scope.dlconttypesets = DlContTypeSet.query();
        $scope.dlcontcatsets = DlContCatSet.query();
        $scope.load = function(id) {
            DlContSCatSet.get({id : id}, function(result) {
                $scope.dlContSCatSet = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:dlContSCatSetUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dlContSCatSet.id != null) {
                DlContSCatSet.update($scope.dlContSCatSet, onSaveSuccess, onSaveError);
            } else {
                DlContSCatSet.save($scope.dlContSCatSet, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
