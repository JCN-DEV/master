'use strict';

angular.module('stepApp')
    .controller('InstPlayGroundInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstPlayGroundInfo', 'InstInfraInfo',
    function ($scope, $rootScope, $stateParams, entity, InstPlayGroundInfo, InstInfraInfo) {
        $scope.instPlayGroundInfo = entity;
        $scope.load = function (id) {
            InstPlayGroundInfo.get({id: id}, function(result) {
                $scope.instPlayGroundInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instPlayGroundInfoUpdate', function(event, result) {
            $scope.instPlayGroundInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
