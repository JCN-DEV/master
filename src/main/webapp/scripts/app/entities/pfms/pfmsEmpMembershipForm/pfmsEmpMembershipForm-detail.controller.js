'use strict';

angular.module('stepApp')
    .controller('PfmsEmpMembershipFormDetailController', function ($scope, $rootScope, $stateParams, entity, PfmsEmpMembershipForm, HrEmployeeInfo, Relationship) {
        $scope.pfmsEmpMembershipForm = entity;
        $scope.load = function (id) {
            PfmsEmpMembershipForm.get({id: id}, function(result) {
                $scope.pfmsEmpMembershipForm = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pfmsEmpMembershipFormUpdate', function(event, result) {
            $scope.pfmsEmpMembershipForm = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
