'use strict';

angular.module('stepApp')
    .controller('AlmEmpLeaveBalanceDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmEmpLeaveBalance', 'HrEmployeeInfo', 'AlmEmpLeaveApplication',
    function ($scope, $rootScope, $stateParams, entity, AlmEmpLeaveBalance, HrEmployeeInfo, AlmEmpLeaveApplication) {
        $scope.almEmpLeaveBalance = entity;
        $scope.load = function (id) {
            AlmEmpLeaveBalance.get({id: id}, function(result) {
                $scope.almEmpLeaveBalance = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almEmpLeaveBalanceUpdate', function(event, result) {
            $scope.almEmpLeaveBalance = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
