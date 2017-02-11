'use strict';

angular.module('stepApp')
    .controller('AlmWeekendConfigurationDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmWeekendConfiguration',
    function ($scope, $rootScope, $stateParams, entity, AlmWeekendConfiguration) {
        $scope.almWeekendConfiguration = entity;
        $scope.load = function (id) {
            AlmWeekendConfiguration.get({id: id}, function(result) {
                $scope.almWeekendConfiguration = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almWeekendConfigurationUpdate', function(event, result) {
            $scope.almWeekendConfiguration = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
