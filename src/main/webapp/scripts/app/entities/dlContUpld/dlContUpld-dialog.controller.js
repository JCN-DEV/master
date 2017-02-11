'use strict';

angular.module('stepApp').controller('DlContUpldDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'DlContUpld', 'DlContTypeSet', 'DlContCatSet', 'InstEmployee', 'DlFileType',
        function($scope, $stateParams, $modalInstance, entity, DlContUpld, DlContTypeSet, DlContCatSet, InstEmployee, DlFileType) {

        $scope.dlContUpld = entity;
        $scope.dlconttypesets = DlContTypeSet.query();
        $scope.dlcontcatsets = DlContCatSet.query();
        $scope.instemployees = InstEmployee.query();
        $scope.dlfiletypes = DlFileType.query();
        $scope.load = function(id) {
            DlContUpld.get({id : id}, function(result) {
                $scope.dlContUpld = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:dlContUpldUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dlContUpld.id != null) {
                DlContUpld.update($scope.dlContUpld, onSaveSuccess, onSaveError);
            } else {
                DlContUpld.save($scope.dlContUpld, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
