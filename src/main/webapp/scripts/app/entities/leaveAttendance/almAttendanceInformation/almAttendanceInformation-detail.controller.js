'use strict';

angular.module('stepApp')
    .controller('AlmAttendanceInformationDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmAttendanceInformation', 'HrEmployeeInfo', 'AlmAttendanceConfiguration', 'AlmShiftSetup', 'AlmAttendanceStatus',
    function ($scope, $rootScope, $stateParams, entity, AlmAttendanceInformation, HrEmployeeInfo, AlmAttendanceConfiguration, AlmShiftSetup, AlmAttendanceStatus) {
        $scope.almAttendanceInformation = entity;
        $scope.load = function (id) {
            AlmAttendanceInformation.get({id: id}, function(result) {
                $scope.almAttendanceInformation = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almAttendanceInformationUpdate', function(event, result) {
            $scope.almAttendanceInformation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
