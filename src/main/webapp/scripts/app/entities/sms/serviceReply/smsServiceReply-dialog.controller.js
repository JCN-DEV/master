'use strict';

angular.module('stepApp').controller('SmsServiceReplyDialogController',
    ['$scope', '$state', '$stateParams', 'entity', 'SmsServiceReply', 'SmsServiceComplaint', 'User','Principal','DateUtils',
        function($scope, $state, $stateParams, entity, SmsServiceReply, SmsServiceComplaint, User, Principal, DateUtils )
        {

        $scope.smsServiceReply = entity;
        $scope.smsservicecomplaints = SmsServiceComplaint.query();

        $scope.load = function(id) {
            SmsServiceReply.get({id : id}, function(result) {
                $scope.smsServiceReply = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:smsServiceReplyUpdate', result);

            $scope.isSaving = false;
            $state.go('smsServiceComplaint');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.smsServiceReply.replyDate = DateUtils.convertLocaleDateToServer(new Date());
            $scope.smsServiceReply.activeStatus = true;
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result) {
                    $scope.smsServiceReply.replier = result;

                    $scope.isSaving = true;
                    if ($scope.smsServiceReply.id != null) {
                        SmsServiceReply.update($scope.smsServiceReply, onSaveSuccess, onSaveError);
                    } else {
                        SmsServiceReply.save($scope.smsServiceReply, onSaveSuccess, onSaveError);
                    }
                });
            });
        };

        $scope.clear = function() {

        };
}]);
