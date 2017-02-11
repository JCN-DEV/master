'use strict';

angular.module('stepApp')
    .controller('UmracRightsSetupDetailController',
        ['$scope', '$rootScope', '$stateParams', 'entity', 'UmracRightsSetup',
        function ($scope, $rootScope, $stateParams, entity, UmracRightsSetup) {
        $scope.umracRightsSetup = entity;
        $scope.load = function (id) {
            UmracRightsSetup.get({id: id}, function(result) {
                $scope.umracRightsSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:umracRightsSetupUpdate', function(event, result) {
            $scope.umracRightsSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
