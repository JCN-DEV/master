'use strict';

angular.module('stepApp').controller('DeoHistLogDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'DeoHistLog', 'Deo', 'District',
        function($scope, $stateParams, $modalInstance, entity, DeoHistLog, Deo, District) {

        $scope.deoHistLog = entity;
        $scope.deos = Deo.query();
        $scope.districts = District.query();
        $scope.load = function(id) {
            DeoHistLog.get({id : id}, function(result) {
                $scope.deoHistLog = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:deoHistLogUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.deoHistLog.id != null) {
                DeoHistLog.update($scope.deoHistLog, onSaveSuccess, onSaveError);
            } else {
                DeoHistLog.save($scope.deoHistLog, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
