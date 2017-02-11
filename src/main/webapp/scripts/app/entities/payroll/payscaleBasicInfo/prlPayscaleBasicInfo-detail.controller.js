'use strict';

angular.module('stepApp')
    .controller('PrlPayscaleBasicInfoDetailController', function ($scope, $rootScope, $stateParams, entity, PrlPayscaleBasicInfo, PrlPayscaleInfo) {
        $scope.prlPayscaleBasicInfo = entity;
        $scope.load = function (id) {
            PrlPayscaleBasicInfo.get({id: id}, function(result) {
                $scope.prlPayscaleBasicInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:prlPayscaleBasicInfoUpdate', function(event, result) {
            $scope.prlPayscaleBasicInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
