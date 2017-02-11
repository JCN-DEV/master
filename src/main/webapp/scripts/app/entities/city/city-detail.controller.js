'use strict';

angular.module('stepApp')
    .controller('CityDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'City', 'Country',
    function ($scope, $rootScope, $stateParams, entity, City, Country) {
        $scope.city = entity;
        $scope.load = function (id) {
            City.get({id: id}, function(result) {
                $scope.city = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:cityUpdate', function(event, result) {
            $scope.city = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
