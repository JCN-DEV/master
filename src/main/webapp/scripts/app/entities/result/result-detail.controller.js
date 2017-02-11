'use strict';

angular.module('stepApp')
    .controller('ResultDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'Result', 'Institute',
    function ($scope, $rootScope, $stateParams, entity, Result, Institute) {
        $scope.result = entity;
        $scope.load = function (id) {
            Result.get({id: id}, function(result) {
                $scope.result = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:resultUpdate', function(event, result) {
            $scope.result = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
