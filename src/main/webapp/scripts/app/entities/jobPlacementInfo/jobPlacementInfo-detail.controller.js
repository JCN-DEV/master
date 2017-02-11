'use strict';

angular.module('stepApp')
    .controller('JobPlacementInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'JobPlacementInfo',
    function ($scope, $rootScope, $stateParams, entity, JobPlacementInfo) {
        $scope.jobPlacementInfo = entity;
        $scope.load = function (id) {
            JobPlacementInfo.get({id: id}, function(result) {
                $scope.jobPlacementInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:jobPlacementInfoUpdate', function(event, result) {
            $scope.jobPlacementInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
