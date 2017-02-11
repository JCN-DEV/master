'use strict';

angular.module('stepApp').controller('DlFileTypeDialogController',
    ['$scope', '$rootScope', '$state', '$stateParams', 'entity', 'DlFileType','DlFileTypeByfileType',
        function($scope, $rootScope, $state, $stateParams, entity, DlFileType,DlFileTypeByfileType) {

        $scope.dlFileType = entity;
        $scope.dlFileType.pStatus = true;
        $scope.load = function(id) {
            DlFileType.get({id : id}, function(result) {
                $scope.dlFileType = result;
            });
        };
          DlFileTypeByfileType.get({fileType: $scope.dlFileType.fileType}, function (dlFileType) {

                      $scope.message = "The  File Type is already exist.";

                  });


        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:dlFileTypeUpdate', result);
            $scope.isSaving = false;
            $state.go('libraryInfo.dlFileType',{},{reload:true});

        };


        var onSaveError = function (result) {
            $scope.isSaving = false;
        };


  $scope.setFileImg = function ($file, dlFileType) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        dlFileType.fileImg = base64Data;
                        dlFileType.fileImgContentType = $file.type;
                        dlFileType.fileImgName = $file.name;

                    });
                };
            }

        };
        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.dlFileType.id != null) {
                DlFileType.update($scope.dlFileType, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.dlFileType.updated');
            } else {
                DlFileType.save($scope.dlFileType, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.dlFileType.created');
            }
        };
}]);
