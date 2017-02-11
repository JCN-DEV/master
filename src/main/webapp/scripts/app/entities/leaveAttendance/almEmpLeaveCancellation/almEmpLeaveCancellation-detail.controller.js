'use strict';

angular.module('stepApp')
    .controller('AlmEmpLeaveCancellationDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmEmpLeaveCancellation', 'HrEmployeeInfo', 'AlmEmpLeaveApplication',
    function ($scope, $rootScope, $stateParams, entity, AlmEmpLeaveCancellation, HrEmployeeInfo, AlmEmpLeaveApplication) {
        $scope.almEmpLeaveCancellation = entity;
        $scope.load = function (id) {
            AlmEmpLeaveCancellation.get({id: id}, function(result) {
                $scope.almEmpLeaveCancellation = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almEmpLeaveCancellationUpdate', function(event, result) {
            $scope.almEmpLeaveCancellation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
