'use strict';

angular.module('stepApp').controller('SmsGatewayDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'SmsGateway',
        function($scope, $stateParams, $modalInstance, entity, SmsGateway) {

        $scope.smsGateway = entity;
        $scope.load = function(id) {
            SmsGateway.get({id : id}, function(result) {
                $scope.smsGateway = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:smsGatewayUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.smsGateway.id != null) {
                SmsGateway.update($scope.smsGateway, onSaveFinished);
            } else {
                SmsGateway.save($scope.smsGateway, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
