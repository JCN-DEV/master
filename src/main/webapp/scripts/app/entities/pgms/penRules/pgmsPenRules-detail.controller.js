'use strict';

angular.module('stepApp')
    .controller('PgmsPenRulesDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'PgmsPenRules',
    function ($scope, $rootScope, $stateParams, entity, PgmsPenRules) {
        $scope.pgmsPenRules = entity;
        $scope.load = function (id) {
            PgmsPenRules.get({id: id}, function(result) {
                $scope.pgmsPenRules = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pgmsPenRulesUpdate', function(event, result) {
            $scope.pgmsPenRules = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
