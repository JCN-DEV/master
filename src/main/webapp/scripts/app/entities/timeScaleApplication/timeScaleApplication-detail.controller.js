'use strict';

angular.module('stepApp')
    .controller('TimeScaleApplicationDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'TimeScaleApplication', 'InstEmployee',
    function ($scope, $rootScope, $stateParams, entity, TimeScaleApplication, InstEmployee) {
        $scope.timeScaleApplication = entity;
        $scope.load = function (id) {
            TimeScaleApplication.get({id: id}, function(result) {
                $scope.timeScaleApplication = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:timeScaleApplicationUpdate', function(event, result) {
            $scope.timeScaleApplication = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
