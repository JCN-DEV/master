'use strict';

angular.module('stepApp')
    .controller('AlmEmpLeaveInitializeDetailController', function ($scope, $rootScope, $stateParams, entity, AlmEmpLeaveInitialize, HrEmployeeInfo, AlmLeaveType) {
        $scope.almEmpLeaveInitialize = entity;
        $scope.load = function (id) {
            AlmEmpLeaveInitialize.get({id: id}, function(result) {
                $scope.almEmpLeaveInitialize = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almEmpLeaveInitializeUpdate', function(event, result) {
            $scope.almEmpLeaveInitialize = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
