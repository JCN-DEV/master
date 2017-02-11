'use strict';

angular.module('stepApp')
    .controller('SisStudentInfoSubjDetailController', function ($scope, $rootScope, $stateParams, entity, SisStudentInfoSubj, SisStudentInfo) {
        $scope.sisStudentInfoSubj = entity;
        $scope.load = function (id) {
            SisStudentInfoSubj.get({id: id}, function(result) {
                $scope.sisStudentInfoSubj = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:sisStudentInfoSubjUpdate', function(event, result) {
            $scope.sisStudentInfoSubj = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
