'use strict';

angular.module('stepApp')
    .controller('AlmHolidayDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmHoliday',
    function ($scope, $rootScope, $stateParams, entity, AlmHoliday) {
        $scope.almHoliday = entity;
        $scope.load = function (id) {
            AlmHoliday.get({id: id}, function(result) {
                $scope.almHoliday = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almHolidayUpdate', function(event, result) {
            $scope.almHoliday = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
