'use strict';

angular.module('stepApp')
    .controller('HrEmpProfMemberInfoApprovalController',
     ['$scope', '$rootScope', '$stateParams','$modalInstance', 'HrEmpProfMemberInfoApproverLog', 'HrEmpProfMemberInfoApprover',
     function ($scope, $rootScope, $stateParams,$modalInstance,  HrEmpProfMemberInfoApproverLog, HrEmpProfMemberInfoApprover) {
        $scope.hrEmpProfMemberInfo = {};
        $scope.hrEmpProfMemberInfoLog = {};
        $scope.isApproved = true;

        $scope.load = function () {
            HrEmpProfMemberInfoApproverLog.get({entityId: $stateParams.id}, function(result)
            {
                console.log("HrEmpProfMemberInfoApproverLog");
                $scope.hrEmpProfMemberInfo = result.entityObject;
                $scope.hrEmpProfMemberInfoLog = result.entityLogObject;
            });
        };


        $scope.applyApproval = function (actionType)
        {
            var approvalObj = $scope.initApprovalObject($scope.hrEmpProfMemberInfo.id, $scope.logComments, actionType);
            console.log("Professional membership approval processing..."+JSON.stringify(approvalObj));

            HrEmpProfMemberInfoApprover.update(approvalObj, function(result)
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


        var unsubscribe = $rootScope.$on('stepApp:hrEmpProfMemberInfoUpdate', function(event, result) {
            $scope.hrEmpProfMemberInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

    }]);
