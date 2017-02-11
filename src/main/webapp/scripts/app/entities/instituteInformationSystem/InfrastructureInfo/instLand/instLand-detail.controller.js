'use strict';

angular.module('stepApp')
    .controller('InstLandDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstLand',
    function ($scope, $rootScope, $stateParams, entity, InstLand) {
        $scope.instLand = entity;
        $scope.load = function (id) {
            InstLand.get({id: id}, function(result) {
                $scope.instLand = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instLandUpdate', function(event, result) {
            $scope.instLand = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
