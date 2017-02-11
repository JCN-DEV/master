'use strict';

angular.module('stepApp')
    .controller('InstShopInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstShopInfo', 'InstInfraInfo',
    function ($scope, $rootScope, $stateParams, entity, InstShopInfo, InstInfraInfo) {
        $scope.instShopInfo = entity;
        $scope.load = function (id) {
            InstShopInfo.get({id: id}, function(result) {
                $scope.instShopInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instShopInfoUpdate', function(event, result) {
            $scope.instShopInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
