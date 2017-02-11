'use strict';

angular.module('stepApp')
    .controller('UmracIdentitySetupDetailController',
        ['$scope', '$rootScope', '$stateParams', 'entity', 'UmracIdentitySetup',
        function ($scope, $rootScope, $stateParams, entity, UmracIdentitySetup) {
        $scope.umracIdentitySetup = entity;
        $scope.load = function (id) {
            UmracIdentitySetup.get({id: id}, function(result) {
                $scope.umracIdentitySetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:umracIdentitySetupUpdate', function(event, result) {
            $scope.umracIdentitySetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
