'use strict';

angular.module('stepApp')
    .controller('InstAdmInfoDetailController',
    ['$scope','$rootScope','$stateParams','entity','InstAdmInfo','Institute',
    function ($scope, $rootScope, $stateParams, entity, InstAdmInfo, Institute) {
        $scope.instAdmInfo = entity;
        $scope.load = function (id) {
            InstAdmInfo.get({id: id}, function(result) {
                $scope.instAdmInfo = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instAdmInfoUpdate', function(event, result) {
            $scope.instAdmInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
