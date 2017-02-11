'use strict';

angular.module('stepApp')
    .controller('PfmsEmpMembershipDetailController', function ($scope, $rootScope, $stateParams, entity, PfmsEmpMembership, HrEmployeeInfo) {
        $scope.pfmsEmpMembership = entity;
        $scope.load = function (id) {
            PfmsEmpMembership.get({id: id}, function(result) {
                $scope.pfmsEmpMembership = result;
            });
        };
        console.log($scope.pfmsEmpMembership);
        var unsubscribe = $rootScope.$on('stepApp:pfmsEmpMembershipUpdate', function(event, result) {
            $scope.pfmsEmpMembership = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
