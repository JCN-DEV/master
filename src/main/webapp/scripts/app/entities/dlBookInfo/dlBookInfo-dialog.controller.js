'use strict';

angular.module('stepApp').controller('DlBookInfoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'DlBookInfo', 'DlContTypeSet', 'DlContCatSet', 'DlContSCatSet',
        function($scope, $stateParams, $modalInstance, entity, DlBookInfo, DlContTypeSet, DlContCatSet, DlContSCatSet) {

        $scope.dlBookInfo = entity;
        $scope.dlconttypesets = DlContTypeSet.query();
        $scope.dlcontcatsets = DlContCatSet.query();
        $scope.dlcontscatsets = DlContSCatSet.query();
        $scope.load = function(id) {
            DlBookInfo.get({id : id}, function(result) {
                $scope.dlBookInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:dlBookInfoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dlBookInfo.id != null) {
                DlBookInfo.update($scope.dlBookInfo, onSaveSuccess, onSaveError);
            } else {
                DlBookInfo.save($scope.dlBookInfo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
