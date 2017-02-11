'use strict';

angular.module('stepApp').controller('CertNameDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'CertName',
        function($scope, $stateParams, $modalInstance, entity, CertName) {

        $scope.certName = entity;
        $scope.load = function(id) {
            CertName.get({id : id}, function(result) {
                $scope.certName = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:certNameUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.certName.id != null) {
                CertName.update($scope.certName, onSaveSuccess, onSaveError);
            } else {
                CertName.save($scope.certName, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
