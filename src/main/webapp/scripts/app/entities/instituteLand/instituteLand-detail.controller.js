'use strict';

angular.module('stepApp')
    .controller('InstituteLandDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'InstituteLand',
    function ($scope, $rootScope, $stateParams, entity, InstituteLand) {
        $scope.instituteLand = entity;
        $scope.load = function (id) {
            InstituteLand.get({id: id}, function(result) {
                $scope.instituteLand = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instituteLandUpdate', function(event, result) {
            $scope.instituteLand = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
