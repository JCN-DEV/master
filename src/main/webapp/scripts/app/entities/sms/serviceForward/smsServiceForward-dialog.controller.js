'use strict';

angular.module('stepApp').controller('SmsServiceForwardDialogController',
    ['$scope', '$state', '$stateParams', 'entity', 'SmsServiceForward', 'SmsServiceComplaint', 'User', 'SmsServiceDepartment','Principal','DateUtils',
        function($scope, $state, $stateParams, entity, SmsServiceForward, SmsServiceComplaint, User, SmsServiceDepartment,Principal, DateUtils) {

        $scope.smsServiceForward = entity;
        $scope.smsservicecomplaints = SmsServiceComplaint.query();
        $scope.users = User.query();
        $scope.smsservicedepartments = SmsServiceDepartment.query();
        $scope.load = function(id) {
            SmsServiceForward.get({id : id}, function(result) {
                $scope.smsServiceForward = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:smsServiceForwardUpdate', result);
            $scope.isSaving = false;
            $state.go('smsServiceComplaint');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            if($scope.smsServiceForward.smsServiceDepartment==null)
            {
                $scope.responseMessage = "Please select service department!!!";
            }
            else if($scope.smsServiceForward.serviceStatus==null)
            {
                $scope.responseMessage = "Please select status!!!";
            }
            else
            {
                $scope.smsServiceForward.forwardDate = DateUtils.convertLocaleDateToServer(new Date());
                $scope.smsServiceForward.activeStatus = true;
                Principal.identity().then(function (account)
                {
                    User.get({login: account.login}, function (result)
                    {
                        $scope.smsServiceForward.forwarder = result;

                        $scope.isSaving = true;
                        if ($scope.smsServiceForward.id != null) {
                            SmsServiceForward.update($scope.smsServiceForward, onSaveSuccess, onSaveError);
                        } else {
                            SmsServiceForward.save($scope.smsServiceForward, onSaveSuccess, onSaveError);
                        }
                    });
                });
            }



        };

        $scope.clear = function() {

        };
}]);
