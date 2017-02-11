'use strict';

angular.module('stepApp')
    .controller('AlmLeaveRuleDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmLeaveRule', 'AlmLeaveType', 'AlmEarningFrequency', 'AlmEarningMethod',
    function ($scope, $rootScope, $stateParams, entity, AlmLeaveRule, AlmLeaveType, AlmEarningFrequency, AlmEarningMethod) {
        $scope.almLeaveRule = entity;
        $scope.load = function (id) {
            AlmLeaveRule.get({id: id}, function(result) {
                $scope.almLeaveRule = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almLeaveRuleUpdate', function(event, result) {
            $scope.almLeaveRule = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
