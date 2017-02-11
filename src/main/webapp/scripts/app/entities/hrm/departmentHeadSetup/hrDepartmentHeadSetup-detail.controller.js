'use strict';

angular.module('stepApp')
    .controller('HrDepartmentHeadSetupDetailController', function ($scope, $rootScope, $stateParams, entity, HrDepartmentHeadSetup) {
        $scope.hrDepartmentHeadSetup = entity;
        $scope.load = function (id) {
            HrDepartmentHeadSetup.get({id: id}, function(result) {
                $scope.hrDepartmentHeadSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrDepartmentHeadSetupUpdate', function(event, result) {
            $scope.hrDepartmentHeadSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
