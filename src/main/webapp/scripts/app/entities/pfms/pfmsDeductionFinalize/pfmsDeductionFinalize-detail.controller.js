'use strict';

angular.module('stepApp')
    .controller('PfmsDeductionFinalizeDetailController', function ($scope, $rootScope, $stateParams, entity, PfmsDeductionFinalize, HrEmployeeInfo) {
        $scope.pfmsDeductionFinalize = entity;
        $scope.load = function (id) {
            PfmsDeductionFinalize.get({id: id}, function(result) {
                $scope.pfmsDeductionFinalize = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pfmsDeductionFinalizeUpdate', function(event, result) {
            $scope.pfmsDeductionFinalize = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
