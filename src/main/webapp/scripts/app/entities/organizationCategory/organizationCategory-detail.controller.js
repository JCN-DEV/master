'use strict';

angular.module('stepApp')
    .controller('OrganizationCategoryDetailController',
    ['$scope','$rootScope','$stateParams','entity','OrganizationCategory',
    function ($scope, $rootScope, $stateParams, entity, OrganizationCategory) {
        $scope.organizationCategory = entity;
        $scope.load = function (id) {
            OrganizationCategory.get({id: id}, function(result) {
                $scope.organizationCategory = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:organizationCategoryUpdate', function(event, result) {
            $scope.organizationCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
