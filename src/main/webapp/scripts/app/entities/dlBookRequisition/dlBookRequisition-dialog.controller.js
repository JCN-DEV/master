'use strict';

angular.module('stepApp').controller('DlBookRequisitionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'DlBookRequisition', 'DlContTypeSet', 'DlContCatSet', 'DlContSCatSet',
        function($scope, $stateParams, $modalInstance, entity, DlBookRequisition, DlContTypeSet, DlContCatSet, DlContSCatSet) {

        $scope.dlBookRequisition = entity;
        $scope.dlconttypesets = DlContTypeSet.query();
        $scope.dlcontcatsets = DlContCatSet.query();
        $scope.dlcontscatsets = DlContSCatSet.query();
        $scope.load = function(id) {
            DlBookRequisition.get({id : id}, function(result) {
                $scope.dlBookRequisition = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:dlBookRequisitionUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.dlBookRequisition.id != null) {
                DlBookRequisition.update($scope.dlBookRequisition, onSaveFinished);
            } else {
                DlBookRequisition.save($scope.dlBookRequisition, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
