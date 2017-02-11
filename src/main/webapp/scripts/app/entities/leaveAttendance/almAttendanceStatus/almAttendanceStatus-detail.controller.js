'use strict';

angular.module('stepApp')
    .controller('AlmAttendanceStatusDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmAttendanceStatus',
    function ($scope, $rootScope, $stateParams, entity, AlmAttendanceStatus) {
        $scope.almAttendanceStatus = entity;
        $scope.load = function (id) {
            AlmAttendanceStatus.get({id: id}, function(result) {
                $scope.almAttendanceStatus = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almAttendanceStatusUpdate', function(event, result) {
            $scope.almAttendanceStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
