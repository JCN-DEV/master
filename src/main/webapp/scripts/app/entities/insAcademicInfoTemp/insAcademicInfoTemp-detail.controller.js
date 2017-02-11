'use strict';

angular.module('stepApp')
    .controller('InsAcademicInfoTempDetailController', function ($scope, $rootScope, $stateParams, entity, InsAcademicInfoTemp, Institute) {
        $scope.insAcademicInfoTemp = entity;
        $scope.load = function (id) {
            InsAcademicInfoTemp.get({id: id}, function(result) {
                $scope.insAcademicInfoTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:insAcademicInfoTempUpdate', function(event, result) {
            $scope.insAcademicInfoTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
