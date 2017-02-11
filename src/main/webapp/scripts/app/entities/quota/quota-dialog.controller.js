'use strict';

angular.module('stepApp').controller('QuotaDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'DataUtils', 'entity', 'Quota',
        function($scope, $stateParams, $modalInstance, DataUtils, entity, Quota) {

        $scope.quota = entity;
        $scope.load = function(id) {
            Quota.get({id : id}, function(result) {
                $scope.quota = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:quotaUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.quota.id != null) {
                Quota.update($scope.quota, onSaveSuccess, onSaveError);
            } else {
                Quota.save($scope.quota, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setCertificate = function ($file, quota) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        quota.certificate = base64Data;
                        quota.certificateContentType = $file.type;
                    });
                };
            }
        };
}]);
