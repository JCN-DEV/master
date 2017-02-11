'use strict';

angular.module('stepApp')
    .controller('InstituteShopInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstituteShopInfo', 'InstituteInfraInfo',
    function ($scope, $rootScope, $stateParams, entity, InstituteShopInfo, InstituteInfraInfo) {
        $scope.instituteShopInfo = entity;
        $scope.load = function (id) {
            InstituteShopInfo.get({id: id}, function(result) {
                $scope.instituteShopInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instituteShopInfoUpdate', function(event, result) {
            $scope.instituteShopInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
