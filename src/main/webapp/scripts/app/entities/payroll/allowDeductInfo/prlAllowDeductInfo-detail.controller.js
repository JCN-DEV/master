'use strict';

angular.module('stepApp')
    .controller('PrlAllowDeductInfoDetailController', function ($scope, $rootScope, $stateParams, entity, PrlAllowDeductInfo, HrGradeSetup) {
        $scope.prlAllowDeductInfo = entity;
        $scope.load = function (id) {
            PrlAllowDeductInfo.get({id: id}, function(result) {
                $scope.prlAllowDeductInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:prlAllowDeductInfoUpdate', function(event, result) {
            $scope.prlAllowDeductInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
