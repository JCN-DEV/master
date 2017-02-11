'use strict';

angular.module('stepApp')
    .controller('InstLevelDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstLevel',
    function ($scope, $rootScope, $stateParams, entity, InstLevel) {
        $scope.instLevel = entity;
        $scope.load = function (id) {
            InstLevel.get({id: id}, function(result) {
                $scope.instLevel = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instLevelUpdate', function(event, result) {
            $scope.instLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
