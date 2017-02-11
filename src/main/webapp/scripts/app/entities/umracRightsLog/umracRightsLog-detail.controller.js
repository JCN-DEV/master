'use strict';

angular.module('stepApp')
    .controller('UmracRightsLogDetailController',
        ['$scope', '$rootScope', '$stateParams', 'entity', 'UmracRightsLog',
        function ($scope, $rootScope, $stateParams, entity, UmracRightsLog) {
        $scope.umracRightsLog = entity;
        $scope.load = function (id) {
            UmracRightsLog.get({id: id}, function(result) {
                $scope.umracRightsLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:umracRightsLogUpdate', function(event, result) {
            $scope.umracRightsLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
