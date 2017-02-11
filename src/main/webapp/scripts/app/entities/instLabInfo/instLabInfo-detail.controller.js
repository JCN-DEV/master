'use strict';

angular.module('stepApp')
    .controller('InstLabInfoDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstLabInfo', 'InstInfraInfo',
    function ($scope, $rootScope, $stateParams, entity, InstLabInfo, InstInfraInfo) {
        $scope.instLabInfo = entity;
        $scope.load = function (id) {
            InstLabInfo.get({id: id}, function(result) {
                $scope.instLabInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instLabInfoUpdate', function(event, result) {
            $scope.instLabInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
