'use strict';

angular.module('stepApp')
    .controller('UmracSubmoduleSetupDetailController',
        ['$scope', '$rootScope', '$stateParams', 'entity', 'UmracSubmoduleSetup', 'UmracModuleSetup',
        function ($scope, $rootScope, $stateParams, entity, UmracSubmoduleSetup, UmracModuleSetup) {
        $scope.umracSubmoduleSetup = entity;
        $scope.load = function (id) {
            UmracSubmoduleSetup.get({id: id}, function(result) {
                $scope.umracSubmoduleSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:umracSubmoduleSetupUpdate', function(event, result) {
            $scope.umracSubmoduleSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
