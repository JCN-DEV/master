'use strict';

angular.module('stepApp')
    .controller('InstCategoryDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstCategory',
     function ($scope, $rootScope, $stateParams, entity, InstCategory) {
        $scope.instCategory = entity;
        $scope.load = function (id) {
            InstCategory.get({id: id}, function(result) {
                $scope.instCategory = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instCategoryUpdate', function(event, result) {
            $scope.instCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
