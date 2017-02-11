'use strict';

angular.module('stepApp')
    .controller('InstGovernBodyDetailController',
    ['$scope','$rootScope','$stateParams','entity','InstGovernBody','Institute',
    function ($scope, $rootScope, $stateParams, entity, InstGovernBody, Institute) {
        $scope.instGovernBody = entity;
        $scope.load = function (id) {
            InstGovernBody.get({id: id}, function(result) {
                $scope.instGovernBody = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instGovernBodyUpdate', function(event, result) {
            $scope.instGovernBody = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
