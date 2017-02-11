'use strict';

angular.module('stepApp')
    .controller('VclRequisitionDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'VclRequisition', 'User',
    function ($scope, $rootScope, $stateParams, entity, VclRequisition, User) {
        $scope.vclRequisition = entity;
        $scope.load = function (id) {
            VclRequisition.get({id: id}, function(result) {
                $scope.vclRequisition = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:vclRequisitionUpdate', function(event, result) {
            $scope.vclRequisition = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
