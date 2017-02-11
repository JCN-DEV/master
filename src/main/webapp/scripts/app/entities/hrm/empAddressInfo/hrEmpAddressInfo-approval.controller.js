'use strict';

angular.module('stepApp')
    .controller('HrEmpAddressInfoApprovalController',
    ['$scope', '$rootScope', '$stateParams', 'HrEmpAddressInfoApproverLog', 'HrEmpAddressInfoApprover', '$modalInstance',
    function ($scope, $rootScope, $stateParams, HrEmpAddressInfoApproverLog, HrEmpAddressInfoApprover, $modalInstance) {
        $scope.hrEmpAddressInfo = {};
        $scope.hrEmpAddressInfoLog = {};
        $scope.isApproved = true;
        $scope.load = function () {
            HrEmpAddressInfoApproverLog.get({entityId: $stateParams.id}, function(result)
            {
                console.log("HrEmpAddressInfoApproverLog");
                $scope.hrEmpAddressInfo = result.entityObject;
                $scope.hrEmpAddressInfoLog = result.entityLogObject;
            });
        };


        $scope.applyApproval = function (actionType)
        {
            var approvalObj = $scope.initApprovalObject($scope.hrEmpAddressInfo.id, $scope.logComments, actionType);
            console.log("Address approval processing..."+JSON.stringify(approvalObj));

            HrEmpAddressInfoApprover.update(approvalObj, function(result)
            {
                $modalInstance.dismiss('cancel');
                $rootScope.$emit('onEntityApprovalProcessCompleted', result);
            });
            $modalInstance.dismiss('cancel');
        };

        $scope.initApprovalObject = function(entityId, logComments, actionType)
        {
            return {
                entityId: entityId,
                logComments:logComments,
                actionType:actionType
            };
        };

        $scope.load();

        var unsubscribe = $rootScope.$on('stepApp:hrEmpAddressInfoUpdate', function(event, result) {
            $scope.hrEmpAddressInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

    }]);
