'use strict';

angular.module('stepApp').controller('PgmsAppRetirmntAttachDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'PgmsAppRetirmntAttach', 'PgmsRetirmntAttachInfo',
        function($scope, $stateParams, $state, entity, PgmsAppRetirmntAttach, PgmsRetirmntAttachInfo) {

        $scope.pgmsAppRetirmntAttach = entity;
        $scope.pgmsretirmntattachinfos = PgmsRetirmntAttachInfo.query();
        $scope.load = function(id) {
            PgmsAppRetirmntAttach.get({id : id}, function(result) {
                $scope.pgmsAppRetirmntAttach = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:pgmsAppRetirmntAttachUpdate', result);
            $scope.isSaving = false;
            $state.go("pgmsAppRetirmntAttach");
        };

        var onSaveError = function (result) {
             $scope.isSaving = false;
        };

        $scope.save = function () {
            if ($scope.pgmsAppRetirmntAttach.id != null) {
                PgmsAppRetirmntAttach.update($scope.pgmsAppRetirmntAttach, onSaveFinished, onSaveError);
            } else {
                PgmsAppRetirmntAttach.save($scope.pgmsAppRetirmntAttach, onSaveFinished, onSaveError);
            }
        };

        $scope.clear = function() {
            $state.dismiss('cancel');
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

        $scope.setAttachment = function ($file, pgmsAppRetirmntAttach) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        pgmsAppRetirmntAttach.attachment = base64Data;
                        pgmsAppRetirmntAttach.attachmentContentType = $file.type;
                    });
                };
            }
        };
}]);
