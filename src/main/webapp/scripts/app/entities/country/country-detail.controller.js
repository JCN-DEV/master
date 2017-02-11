'use strict';

angular.module('stepApp')
    .controller('CountryDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'Country',
    function ($scope, $rootScope, $stateParams, entity, Country) {
        $scope.country = entity;
        $scope.load = function (id) {
            Country.get({id: id}, function(result) {
                $scope.country = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:countryUpdate', function(event, result) {
            $scope.country = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
