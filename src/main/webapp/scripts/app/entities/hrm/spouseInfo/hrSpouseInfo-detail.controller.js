'use strict';

angular.module('stepApp')
    .controller('HrSpouseInfoDetailController', function ($scope, $rootScope, $stateParams, entity, HrSpouseInfo, HrEmployeeInfo) {
        $scope.hrSpouseInfo = entity;
        $scope.load = function (id) {
            HrSpouseInfo.get({id: id}, function(result) {
                $scope.hrSpouseInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrSpouseInfoUpdate', function(event, result) {
            $scope.hrSpouseInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
