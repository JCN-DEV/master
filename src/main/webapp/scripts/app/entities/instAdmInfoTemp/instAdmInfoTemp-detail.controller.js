'use strict';

angular.module('stepApp')
    .controller('InstAdmInfoTempDetailController', function ($scope, $rootScope, $stateParams, entity, InstAdmInfoTemp, Institute) {
        $scope.instAdmInfoTemp = entity;
        $scope.load = function (id) {
            InstAdmInfoTemp.get({id: id}, function(result) {
                $scope.instAdmInfoTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instAdmInfoTempUpdate', function(event, result) {
            $scope.instAdmInfoTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
