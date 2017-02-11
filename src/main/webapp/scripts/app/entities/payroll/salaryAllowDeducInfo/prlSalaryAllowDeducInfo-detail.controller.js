'use strict';

angular.module('stepApp')
    .controller('PrlSalaryAllowDeducInfoDetailController', function ($scope, $rootScope, $stateParams, entity, PrlSalaryAllowDeducInfo, PrlSalaryStructureInfo, PrlAllowDeductInfo) {
        $scope.prlSalaryAllowDeducInfo = entity;
        $scope.load = function (id) {
            PrlSalaryAllowDeducInfo.get({id: id}, function(result) {
                $scope.prlSalaryAllowDeducInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:prlSalaryAllowDeducInfoUpdate', function(event, result) {
            $scope.prlSalaryAllowDeducInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
