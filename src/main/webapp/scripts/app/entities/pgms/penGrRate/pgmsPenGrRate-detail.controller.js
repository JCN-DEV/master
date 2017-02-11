'use strict';

angular.module('stepApp')
    .controller('PgmsPenGrRateDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'PgmsPenGrRate',
    function ($scope, $rootScope, $stateParams, entity, PgmsPenGrRate) {
        $scope.pgmsPenGrRate = entity;
        $scope.load = function (id) {
            PgmsPenGrRate.get({id: id}, function(result) {
                $scope.pgmsPenGrRate = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pgmsPenGrRateUpdate', function(event, result) {
            $scope.pgmsPenGrRate = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
