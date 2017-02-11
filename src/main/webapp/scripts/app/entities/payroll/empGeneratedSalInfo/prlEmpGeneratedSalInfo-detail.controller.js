'use strict';

angular.module('stepApp')
    .controller('PrlEmpGeneratedSalInfoDetailController', function ($scope, $rootScope, $stateParams, entity, PrlEmpGeneratedSalInfo, PrlGeneratedSalaryInfo, PrlSalaryStructureInfo, HrEmployeeInfo) {
        $scope.prlEmpGeneratedSalInfo = entity;
        $scope.load = function (id) {
            PrlEmpGeneratedSalInfo.get({id: id}, function(result) {
                $scope.prlEmpGeneratedSalInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:prlEmpGeneratedSalInfoUpdate', function(event, result) {
            $scope.prlEmpGeneratedSalInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
