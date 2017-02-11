'use strict';

angular.module('stepApp').controller('InstEmpEduQualiDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'DataUtils', 'entity', 'InstEmpEduQuali', 'InstEmployee',
        function($scope, $stateParams, $modalInstance, DataUtils, entity, InstEmpEduQuali, InstEmployee) {

        $scope.instEmpEduQuali = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.load = function(id) {
            InstEmpEduQuali.get({id : id}, function(result) {
                $scope.instEmpEduQuali = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmpEduQualiUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.instEmpEduQuali.id != null) {
                InstEmpEduQuali.update($scope.instEmpEduQuali, onSaveSuccess, onSaveError);
            } else {
                InstEmpEduQuali.save($scope.instEmpEduQuali, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setCertificateCopy = function ($file, instEmpEduQuali) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        instEmpEduQuali.certificateCopy = base64Data;
                        instEmpEduQuali.certificateCopyContentType = $file.type;
                    });
                };
            }
        };
}]);
