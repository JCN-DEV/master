'use strict';

angular.module('stepApp')
    .controller('AlmLeaveTypeDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmLeaveType',
    function ($scope, $rootScope, $stateParams, entity, AlmLeaveType) {
        $scope.almLeaveType = entity;
        $scope.load = function (id) {
            AlmLeaveType.get({id: id}, function(result) {
                $scope.almLeaveType = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almLeaveTypeUpdate', function(event, result) {
            $scope.almLeaveType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
