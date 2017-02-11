'use strict';

angular.module('stepApp')
    .controller('UmracActionSetupDetailController',
        ['$scope', '$rootScope', '$stateParams', 'entity', 'UmracActionSetup',
        function ($scope, $rootScope, $stateParams, entity, UmracActionSetup) {
        $scope.umracActionSetup = entity;
        $scope.load = function (id) {
            UmracActionSetup.get({id: id}, function(result) {
                $scope.umracActionSetup = result;

            });
        };
        var unsubscribe = $rootScope.$on('stepApp:umracActionSetupUpdate', function(event, result) {
            $scope.umracActionSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
