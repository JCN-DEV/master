'use strict';

angular.module('stepApp')
    .controller('HrDepartmentHeadInfoDetailController', function ($scope, $rootScope, $stateParams, entity, HrDepartmentHeadInfo, HrDepartmentSetup, HrEmployeeInfo) {
        $scope.hrDepartmentHeadInfo = entity;
        $scope.load = function (id) {
            HrDepartmentHeadInfo.get({id: id}, function(result) {
                $scope.hrDepartmentHeadInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrDepartmentHeadInfoUpdate', function(event, result) {
            $scope.hrDepartmentHeadInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
