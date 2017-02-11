'use strict';

angular.module('stepApp').controller('InstEmplHistDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'DataUtils', 'entity', 'InstEmplHist', 'InstEmployee',
        function($scope, $stateParams, $modalInstance, DataUtils, entity, InstEmplHist, InstEmployee) {

        $scope.instEmplHist = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.load = function(id) {
            InstEmplHist.get({id : id}, function(result) {
                $scope.instEmplHist = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmplHistUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instEmplHist.id != null) {
                InstEmplHist.update($scope.instEmplHist, onSaveSuccess, onSaveError);
            } else {
                InstEmplHist.save($scope.instEmplHist, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setCertificateCopy = function ($file, instEmplHist) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        instEmplHist.certificateCopy = base64Data;
                        instEmplHist.certificateCopyContentType = $file.type;
                    });
                };
            }
        };
}]);
