'use strict';

angular.module('stepApp').controller('SmsServiceNameDialogController',
    ['$scope', '$state', '$stateParams', 'entity', 'SmsServiceName','SmsServiceTypeByStatus',
        function($scope, $state, $stateParams, entity, SmsServiceName, SmsServiceTypeByStatus) {

        $scope.smsServiceName = entity;
        $scope.smsservicetypes = SmsServiceTypeByStatus.query({stat:true});
        $scope.load = function(id) {
            SmsServiceName.get({id : id}, function(result) {
                $scope.smsServiceName = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:smsServiceNameUpdate', result);
            $scope.isSaving = false;
            $state.go('smsServiceName');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
        	console.log("Saving: "+$scope.isSaving);
            if($scope.smsServiceName.smsServiceType==null)
            {
                $scope.responseMessage = "Please select service type!!!";
            }
            else
            {
                $scope.isSaving = true;
                console.log($scope.smsServiceName);
                if ($scope.smsServiceName.id != null) {
                    SmsServiceName.update($scope.smsServiceName, onSaveSuccess, onSaveError);
                } else {
                    SmsServiceName.save($scope.smsServiceName, onSaveSuccess, onSaveError);
                }
            }
        };

        $scope.clear = function() {

        };
}]);
