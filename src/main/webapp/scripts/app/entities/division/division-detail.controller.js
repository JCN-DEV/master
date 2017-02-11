'use strict';

angular.module('stepApp')
    .controller('DivisionDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'Division', 'District', 'Country',
    function ($scope, $rootScope, $stateParams, entity, Division, District, Country) {
        $scope.division = entity;
        $scope.load = function (id) {
            Division.get({id: id}, function(result) {
                $scope.division = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:divisionUpdate', function(event, result) {
            $scope.division = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
