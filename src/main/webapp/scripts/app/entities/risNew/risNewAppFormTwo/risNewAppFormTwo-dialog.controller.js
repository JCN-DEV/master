'use strict';

angular.module('stepApp').controller('RisNewAppFormTwoDialogController',
    ['$scope','$state', '$stateParams', 'entity', 'RisNewAppFormTwo',
        function($scope,$state, $stateParams, entity, RisNewAppFormTwo) {

        $scope.risNewAppFormTwo = entity;
        $scope.load = function(id) {
            RisNewAppFormTwo.get({id : id}, function(result) {
                $scope.risNewAppFormTwo = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:risNewAppFormTwoUpdate', result);
            $state.go('risNew.risNewAppFormTwo', null, { reload: true });
        };

        $scope.save = function () {
            if ($scope.risNewAppFormTwo.id != null) {
                RisNewAppFormTwo.update($scope.risNewAppFormTwo, onSaveFinished);
            } else {
                RisNewAppFormTwo.save($scope.risNewAppFormTwo, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $state.go('risNew.risNewAppFormTwo', null, { reload: true });
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

        $scope.setBankInvoice = function ($file, risNewAppFormTwo) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        risNewAppFormTwo.bankInvoice = base64Data;
                        risNewAppFormTwo.bankInvoiceContentType = $file.type;
                    });
                };
            }
        };

        $scope.setSignature = function ($file, risNewAppFormTwo) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        risNewAppFormTwo.signature = base64Data;
                        risNewAppFormTwo.signatureContentType = $file.type;
                    });
                };
            }
        };
}]);
