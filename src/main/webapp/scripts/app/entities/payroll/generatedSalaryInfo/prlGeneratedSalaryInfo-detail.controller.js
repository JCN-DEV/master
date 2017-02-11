'use strict';

angular.module('stepApp')
    .controller('PrlGeneratedSalaryInfoDetailController', function ($scope, $rootScope, $stateParams, entity, PrlGeneratedSalaryInfo) {
        $scope.prlGeneratedSalaryInfo = entity;
        $scope.load = function (id) {
            PrlGeneratedSalaryInfo.get({id: id}, function(result) {
                $scope.prlGeneratedSalaryInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:prlGeneratedSalaryInfoUpdate', function(event, result) {
            $scope.prlGeneratedSalaryInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
