'use strict';

angular.module('stepApp').controller('SmsServiceTypeDialogController',
    ['$scope', '$state', '$stateParams', 'entity', 'SmsServiceType',
        function($scope, $state, $stateParams, entity, SmsServiceType) {

        $scope.smsServiceType = entity;
        $scope.load = function(id) {
            SmsServiceType.get({id : id}, function(result) {
                $scope.smsServiceType = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:smsServiceTypeUpdate', result);
            $scope.isSaving = false;
            $state.go('smsServiceType');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.smsServiceType.id != null) {
                SmsServiceType.update($scope.smsServiceType, onSaveSuccess, onSaveError);
            } else {
                SmsServiceType.save($scope.smsServiceType, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {

        };
}]);
