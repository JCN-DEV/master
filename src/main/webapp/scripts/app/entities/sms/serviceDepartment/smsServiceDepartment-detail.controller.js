'use strict';

angular.module('stepApp')
    .controller('SmsServiceDepartmentDetailController',
     ['$scope', '$rootScope', '$stateParams', 'entity', 'SmsServiceDepartment', 'User', 'SmsServiceType', 'SmsServiceName', 'Department',
     function ($scope, $rootScope, $stateParams, entity, SmsServiceDepartment, User, SmsServiceType, SmsServiceName, Department) {
        $scope.smsServiceDepartment = entity;
        $scope.load = function (id) {
            SmsServiceDepartment.get({id: id}, function(result) {
                $scope.smsServiceDepartment = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:smsServiceDepartmentUpdate', function(event, result) {
            $scope.smsServiceDepartment = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
