'use strict';

angular.module('stepApp')
    .controller('MpoApplicationStatusLogDetailController',
    ['$scope','$rootScope','$stateParams','entity','MpoApplicationStatusLog','MpoApplication',
    function ($scope, $rootScope, $stateParams, entity, MpoApplicationStatusLog, MpoApplication) {
        $scope.mpoApplicationStatusLog = entity;
        $scope.load = function (id) {
            MpoApplicationStatusLog.get({id: id}, function(result) {
                $scope.mpoApplicationStatusLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:mpoApplicationStatusLogUpdate', function(event, result) {
            $scope.mpoApplicationStatusLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
