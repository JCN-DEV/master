'use strict';

angular.module('stepApp')
    .controller('HrEmploymentInfoApprovalController',
    ['$scope', '$rootScope', '$stateParams', '$modalInstance', 'HrEmploymentInfoApprover', 'HrEmploymentInfoApproverLog',
    function ($scope, $rootScope, $stateParams, $modalInstance, HrEmploymentInfoApprover, HrEmploymentInfoApproverLog) {
        $scope.hrEmploymentInfo = {};
        $scope.hrEmploymentInfoLog = {};
        $scope.isApproved = true;

        $scope.load = function ()
        {
            HrEmploymentInfoApproverLog.get({entityId: $stateParams.id}, function(result)
            {
                console.log("HrEmploymentInfoApproverLog");
                $scope.hrEmploymentInfo = result.entityObject;
                $scope.hrEmploymentInfoLog = result.entityLogObject;
            });
        };

        $scope.applyApproval = function (actionType)
        {
            var approvalObj = $scope.initApprovalObject($scope.hrEmploymentInfo.id, $scope.logComments, actionType);
            console.log("Employment approval processing..."+JSON.stringify(approvalObj));
            HrEmploymentInfoApprover.update(approvalObj, function(result)
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

        var unsubscribe = $rootScope.$on('stepApp:hrEmploymentInfoUpdate', function(event, result) {
            $scope.hrEmploymentInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };


    }]);
