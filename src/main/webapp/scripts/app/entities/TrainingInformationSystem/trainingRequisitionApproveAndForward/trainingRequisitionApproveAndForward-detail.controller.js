'use strict';

angular.module('stepApp')
    .controller('TrainingRequisitionApproveAndForwardDetailController', function ($scope, $rootScope, $stateParams, entity, TrainingRequisitionApproveAndForward, TrainingRequisitionForm) {
        $scope.trainingRequisitionApproveAndForward = entity;
        $scope.load = function (id) {
            TrainingRequisitionApproveAndForward.get({id: id}, function(result) {
                $scope.trainingRequisitionApproveAndForward = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:trainingRequisitionApproveAndForwardUpdate', function(event, result) {
            $scope.trainingRequisitionApproveAndForward = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
