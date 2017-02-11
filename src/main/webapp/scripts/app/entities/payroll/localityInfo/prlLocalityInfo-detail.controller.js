'use strict';

angular.module('stepApp')
    .controller('PrlLocalityInfoDetailController', function ($scope, $rootScope, $stateParams, entity, PrlLocalityInfo, District) {
        $scope.prlLocalityInfo = entity;
        $scope.load = function (id) {
            PrlLocalityInfo.get({id: id}, function(result) {
                $scope.prlLocalityInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:prlLocalityInfoUpdate', function(event, result) {
            $scope.prlLocalityInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
