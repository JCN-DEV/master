'use strict';

angular.module('stepApp')
    .controller('PrincipalRequirementDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'PrincipalRequirement', 'Employee',
    function ($scope, $rootScope, $stateParams, entity, PrincipalRequirement, Employee) {
        $scope.principalRequirement = entity;
        $scope.load = function (id) {
            PrincipalRequirement.get({id: id}, function(result) {
                $scope.principalRequirement = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:principalRequirementUpdate', function(event, result) {
            $scope.principalRequirement = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);



