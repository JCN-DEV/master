'use strict';

angular.module('stepApp')
    .controller('RelationshipDetailController', function ($scope, $rootScope, $stateParams, entity, Relationship) {
        $scope.relationship = entity;
        $scope.load = function (id) {
            Relationship.get({id: id}, function(result) {
                $scope.relationship = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:relationshipUpdate', function(event, result) {
            $scope.relationship = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
