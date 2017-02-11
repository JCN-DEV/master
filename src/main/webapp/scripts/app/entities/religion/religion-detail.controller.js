'use strict';

angular.module('stepApp')
    .controller('ReligionDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'Religion',
    function ($scope, $rootScope, $stateParams, entity, Religion) {
        $scope.religion = entity;
        $scope.load = function (id) {
            Religion.get({id: id}, function(result) {
                $scope.religion = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:religionUpdate', function(event, result) {
            $scope.religion = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
