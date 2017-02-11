'use strict';

angular.module('stepApp')
    .controller('UmracModuleSetupDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity','UmracModuleSetup',
    function ($scope, $rootScope, $stateParams, entity, UmracModuleSetup) {
        $scope.umracModuleSetup = entity;
        $scope.load = function (id) {
            UmracModuleSetup.get({id: id}, function(result) {
                $scope.umracModuleSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:umracModuleSetupUpdate', function(event, result) {
            $scope.umracModuleSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
