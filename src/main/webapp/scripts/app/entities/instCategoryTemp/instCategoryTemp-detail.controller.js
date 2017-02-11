'use strict';

angular.module('stepApp')
    .controller('InstCategoryTempDetailController', function ($scope, $rootScope, $stateParams, entity, InstCategoryTemp) {
        $scope.instCategoryTemp = entity;
        $scope.load = function (id) {
            InstCategoryTemp.get({id: id}, function(result) {
                $scope.instCategoryTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instCategoryTempUpdate', function(event, result) {
            $scope.instCategoryTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
