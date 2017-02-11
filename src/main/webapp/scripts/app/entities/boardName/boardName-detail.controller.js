'use strict';

angular.module('stepApp')
    .controller('BoardNameDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'BoardName',
     function ($scope, $rootScope, $stateParams, entity, BoardName) {
        $scope.boardName = entity;
        $scope.load = function (id) {
            BoardName.get({id: id}, function(result) {
                $scope.boardName = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:boardNameUpdate', function(event, result) {
            $scope.boardName = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
