'use strict';

angular.module('stepApp')
    .controller('AlmEmpLeaveTypeMapDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmEmpLeaveTypeMap', 'HrEmployeeInfo', 'AlmLeaveGroup', 'AlmLeaveType',
    function ($scope, $rootScope, $stateParams, entity, AlmEmpLeaveTypeMap, HrEmployeeInfo, AlmLeaveGroup, AlmLeaveType) {
        $scope.almEmpLeaveTypeMap = entity;
        $scope.load = function (id) {
            AlmEmpLeaveTypeMap.get({id: id}, function(result) {
                $scope.almEmpLeaveTypeMap = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almEmpLeaveTypeMapUpdate', function(event, result) {
            $scope.almEmpLeaveTypeMap = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
