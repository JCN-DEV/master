'use strict';

angular.module('stepApp')
    .controller('AlmAttendanceConfigurationDetailController',

    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmAttendanceConfiguration', 'HrEmployeeInfo', 'AlmShiftSetup',
    function ($scope, $rootScope, $stateParams, entity, AlmAttendanceConfiguration, HrEmployeeInfo, AlmShiftSetup) {
        $scope.almAttendanceConfiguration = entity;
        $scope.load = function (id) {
            AlmAttendanceConfiguration.get({id: id}, function(result) {
                $scope.almAttendanceConfiguration = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almAttendanceConfigurationUpdate', function(event, result) {
            $scope.almAttendanceConfiguration = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
