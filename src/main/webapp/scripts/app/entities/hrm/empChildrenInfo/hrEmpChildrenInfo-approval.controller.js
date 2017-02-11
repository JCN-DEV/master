'use strict';

angular.module('stepApp')
    .controller('HrEmpChildrenInfoApprovalController',
    ['$scope', '$rootScope', '$stateParams','$modalInstance', 'HrEmpChildrenInfoApprover', 'HrEmpChildrenInfoApproverLog',
    function ($scope, $rootScope, $stateParams,$modalInstance, HrEmpChildrenInfoApprover, HrEmpChildrenInfoApproverLog) {
        $scope.hrEmpChildrenInfo = {};
        $scope.hrEmpChildrenInfoLog = {};
        $scope.isApproved = true;

        $scope.load = function (id)
        {
            HrEmpChildrenInfoApproverLog.get({entityId: $stateParams.id}, function(result)
            {
                console.log("HrEmpChildrenInfoApproverLog");
                $scope.hrEmpChildrenInfo = result.entityObject;
                $scope.hrEmpChildrenInfoLog = result.entityLogObject;
            });
        };


        $scope.applyApproval = function (actionType)
        {
            var approvalObj = $scope.initApprovalObject($scope.hrEmpChildrenInfo.id, $scope.logComments, actionType);
            console.log("Children approval processing..."+JSON.stringify(approvalObj));
            HrEmpChildrenInfoApprover.update(approvalObj, function(result)
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

        var unsubscribe = $rootScope.$on('stepApp:hrEmpChildrenInfoUpdate', function(event, result) {
            $scope.hrEmpChildrenInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

    }]);
