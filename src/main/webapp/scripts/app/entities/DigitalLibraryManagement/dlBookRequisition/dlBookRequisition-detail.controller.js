'use strict';

angular.module('stepApp')
    .controller('DlBookRequisitionDetailController', function ($scope, $rootScope, $stateParams, entity, DlBookRequisition, DlContTypeSet, DlContCatSet, DlContSCatSet) {
        $scope.dlBookRequisition = entity;
        $scope.load = function (id) {
            DlBookRequisition.get({id: id}, function(result) {
                $scope.dlBookRequisition = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:dlBookRequisitionUpdate', function(event, result) {
            $scope.dlBookRequisition = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
