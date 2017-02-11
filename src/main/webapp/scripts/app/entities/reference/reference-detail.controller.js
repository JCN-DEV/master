'use strict';

angular.module('stepApp')
    .controller('ReferenceDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'Reference', 'Employee', 'User',
    function ($scope, $rootScope, $stateParams, entity, Reference, Employee, User) {
        $scope.reference = entity;
        $scope.load = function (id) {
            Reference.get({id: id}, function(result) {
                $scope.reference = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:referenceUpdate', function(event, result) {
            $scope.reference = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
