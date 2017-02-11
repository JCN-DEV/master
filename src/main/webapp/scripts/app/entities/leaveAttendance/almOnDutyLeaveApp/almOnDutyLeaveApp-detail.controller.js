'use strict';

angular.module('stepApp')
    .controller('AlmOnDutyLeaveAppDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AlmOnDutyLeaveApp',
    function ($scope, $rootScope, $stateParams, entity, AlmOnDutyLeaveApp) {
        $scope.almOnDutyLeaveApp = entity;
        $scope.load = function (id) {
            AlmOnDutyLeaveApp.get({id: id}, function(result) {
                $scope.almOnDutyLeaveApp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:almOnDutyLeaveAppUpdate', function(event, result) {
            $scope.almOnDutyLeaveApp = result;

        });
        $scope.$on('$destroy', unsubscribe);

    }]);
