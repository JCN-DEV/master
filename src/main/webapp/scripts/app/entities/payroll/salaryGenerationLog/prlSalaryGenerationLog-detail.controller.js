'use strict';

angular.module('stepApp')
    .controller('PrlSalaryGenerationLogDetailController', function ($scope, $rootScope, $stateParams, entity, PrlSalaryGenerationLog, PrlGeneratedSalaryInfo) {
        $scope.prlSalaryGenerationLog = entity;
        $scope.load = function (id) {
            PrlSalaryGenerationLog.get({id: id}, function(result) {
                $scope.prlSalaryGenerationLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:prlSalaryGenerationLogUpdate', function(event, result) {
            $scope.prlSalaryGenerationLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
