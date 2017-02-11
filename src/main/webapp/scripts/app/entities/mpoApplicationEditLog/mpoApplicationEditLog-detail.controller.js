'use strict';

angular.module('stepApp')
    .controller('MpoApplicationEditLogDetailController',
    ['$scope','$rootScope','$stateParams','entity','MpoApplicationEditLog','MpoApplication',
    function ($scope, $rootScope, $stateParams, entity, MpoApplicationEditLog, MpoApplication) {
        $scope.mpoApplicationEditLog = entity;
        $scope.load = function (id) {
            MpoApplicationEditLog.get({id: id}, function(result) {
                $scope.mpoApplicationEditLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:mpoApplicationEditLogUpdate', function(event, result) {
            $scope.mpoApplicationEditLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
