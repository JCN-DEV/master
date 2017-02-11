'use strict';

angular.module('stepApp')
    .controller('DlBookEditionDetailController', function ($scope, $rootScope, $stateParams, entity, DlBookEdition, DlBookInfo) {
        $scope.dlBookEdition = entity;
        $scope.load = function (id) {
            DlBookEdition.get({id: id}, function(result) {
                $scope.dlBookEdition = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:dlBookEditionUpdate', function(event, result) {
            $scope.dlBookEdition = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
