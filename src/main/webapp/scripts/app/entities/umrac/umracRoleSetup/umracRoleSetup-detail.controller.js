'use strict';

angular.module('stepApp')
    .controller('UmracRoleSetupDetailController',
        ['$scope', '$rootScope', '$stateParams', 'entity', 'UmracRoleSetup',
        function ($scope, $rootScope, $stateParams, entity, UmracRoleSetup) {
        $scope.umracRoleSetup = entity;
        $scope.load = function (id) {
            UmracRoleSetup.get({id: id}, function(result) {
                $scope.umracRoleSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:umracRoleSetupUpdate', function(event, result) {
            $scope.umracRoleSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
