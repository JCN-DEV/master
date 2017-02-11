'use strict';

angular.module('stepApp')
    .controller('PrlOnetimeAllowInfoDetailController', function ($scope, $rootScope, $stateParams, entity, PrlOnetimeAllowInfo) {
        $scope.prlOnetimeAllowInfo = entity;
        $scope.load = function (id) {
            PrlOnetimeAllowInfo.get({id: id}, function(result) {
                $scope.prlOnetimeAllowInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:prlOnetimeAllowInfoUpdate', function(event, result) {
            $scope.prlOnetimeAllowInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
