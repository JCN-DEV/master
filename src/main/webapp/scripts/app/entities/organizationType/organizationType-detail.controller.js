'use strict';

angular.module('stepApp')
    .controller('OrganizationTypeDetailController',
    ['$scope','$rootScope','$stateParams','entity','OrganizationType',
    function ($scope, $rootScope, $stateParams, entity, OrganizationType) {
        $scope.organizationType = entity;
        $scope.load = function (id) {
            OrganizationType.get({id: id}, function(result) {
                $scope.organizationType = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:organizationTypeUpdate', function(event, result) {
            $scope.organizationType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
