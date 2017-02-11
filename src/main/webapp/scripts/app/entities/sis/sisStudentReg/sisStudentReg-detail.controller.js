'use strict';

angular.module('stepApp')
    .controller('SisStudentRegDetailController', function ($scope, $rootScope, $stateParams, entity, SisStudentReg) {
        $scope.sisStudentReg = entity;
        $scope.load = function (id) {
            SisStudentReg.get({id: id}, function(result) {
                $scope.sisStudentReg = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:sisStudentRegUpdate', function(event, result) {
            $scope.sisStudentReg = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
