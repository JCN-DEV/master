'use strict';

angular.module('stepApp').controller('ExternalCVDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ExternalCV',
        function($scope, $stateParams, $modalInstance, entity, ExternalCV) {

        $scope.externalCV = entity;
        $scope.load = function(id) {
            ExternalCV.get({id : id}, function(result) {
                $scope.externalCV = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:externalCVUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.externalCV.id != null) {
                ExternalCV.update($scope.externalCV, onSaveFinished);
            } else {
                ExternalCV.save($scope.externalCV, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.abbreviate = function (text) {
            if (!angular.isString(text)) {
                return '';
            }
            if (text.length < 30) {
                return text;
            }
            return text ? (text.substring(0, 15) + '...' + text.slice(-10)) : '';
        };

        $scope.byteSize = function (base64String) {
            if (!angular.isString(base64String)) {
                return '';
            }
            function endsWith(suffix, str) {
                return str.indexOf(suffix, str.length - suffix.length) !== -1;
            }
            function paddingSize(base64String) {
                if (endsWith('==', base64String)) {
                    return 2;
                }
                if (endsWith('=', base64String)) {
                    return 1;
                }
                return 0;
            }
            function size(base64String) {
                return base64String.length / 4 * 3 - paddingSize(base64String);
            }
            function formatAsBytes(size) {
                return size.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ") + " bytes";
            }

            return formatAsBytes(size(base64String));
        };

        $scope.setCv = function ($file, externalCV) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        externalCV.cv = base64Data;
                        externalCV.cvContentType = $file.type;
                    });
                };
            }
        };
}]);
