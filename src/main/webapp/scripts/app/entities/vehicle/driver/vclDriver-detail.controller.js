'use strict';

angular.module('stepApp')
    .controller('VclDriverDetailController',
    ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'VclDriver',

    function ($scope, $rootScope, $stateParams, DataUtils, entity, VclDriver) {
        $scope.vclDriver = entity;
        $scope.load = function (id) {
            VclDriver.get({id: id}, function(result) {
                $scope.vclDriver = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:vclDriverUpdate', function(event, result) {
            $scope.vclDriver = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    }]);
