'use strict';

angular.module('stepApp').controller('AttachmentDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Attachment', 'InstEmployee', 'AttachmentCategory',
        function($scope, $stateParams, $modalInstance, entity, Attachment, InstEmployee, AttachmentCategory) {

        $scope.attachment = entity;
        $scope.instemployees = InstEmployee.query();
        $scope.attachmentcategorys = AttachmentCategory.query();
        $scope.load = function(id) {
            Attachment.get({id : id}, function(result) {
                $scope.attachment = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:attachmentUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.attachment.id != null) {
                Attachment.update($scope.attachment, onSaveFinished);
            } else {
                Attachment.save($scope.attachment, onSaveFinished);
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

        $scope.setFile = function ($file, attachment) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        attachment.file = base64Data;
                        attachment.fileContentType = $file.type;
                    });
                };
            }
        };
}]);
