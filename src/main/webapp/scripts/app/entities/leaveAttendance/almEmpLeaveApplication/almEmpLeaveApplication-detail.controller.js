'use strict';

angular.module('stepApp')
    .controller('AlmEmpLeaveApplicationDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmEmpLeaveApplication',
    function ($scope, $rootScope, $stateParams, entity, AlmEmpLeaveApplication) {
        $scope.almEmpLeaveApplication = entity;
        $scope.load = function (id) {
            AlmEmpLeaveApplication.get({id: id}, function(result) {
                $scope.almEmpLeaveApplication = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almEmpLeaveApplicationUpdate', function(event, result) {
            $scope.almEmpLeaveApplication = result;
            $rootScope.setErrorMessage('stepApp.almEmpLeaveApplication.deleted');
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
