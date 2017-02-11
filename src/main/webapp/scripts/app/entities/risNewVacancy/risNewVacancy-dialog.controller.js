'use strict';

angular.module('stepApp').controller('RisNewVacancyDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'RisNewVacancy', 'HrDepartmentSetup',
        function($scope, $stateParams, $modalInstance, entity, RisNewVacancy, HrDepartmentSetup) {

        $scope.risNewVacancy = entity;
        $scope.hrdepartmentsetups = HrDepartmentSetup.query();
        $scope.load = function(id) {
            RisNewVacancy.get({id : id}, function(result) {
                $scope.risNewVacancy = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:risNewVacancyUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.risNewVacancy.id != null) {
                RisNewVacancy.update($scope.risNewVacancy, onSaveFinished);
            } else {
                RisNewVacancy.save($scope.risNewVacancy, onSaveFinished);
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

        $scope.setAttachment = function ($file, risNewVacancy) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        risNewVacancy.attachment = base64Data;
                        risNewVacancy.attachmentContentType = $file.type;
                    });
                };
            }
        };
}]);
