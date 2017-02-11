'use strict';

angular.module('stepApp')
    .controller('BEdApplicationStatusLogDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'BEdApplicationStatusLog', 'BEdApplication',
    function ($scope, $rootScope, $stateParams, entity, BEdApplicationStatusLog, BEdApplication) {
        $scope.bEdApplicationStatusLog = entity;
        $scope.load = function (id) {
            BEdApplicationStatusLog.get({id: id}, function(result) {
                $scope.bEdApplicationStatusLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:bEdApplicationStatusLogUpdate', function(event, result) {
            $scope.bEdApplicationStatusLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
