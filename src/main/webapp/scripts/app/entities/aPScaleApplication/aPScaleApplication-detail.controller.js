'use strict';

angular.module('stepApp')
    .controller('APScaleApplicationDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'APScaleApplication', 'InstEmployee',
    function ($scope, $rootScope, $stateParams, entity, APScaleApplication, InstEmployee) {
        $scope.aPScaleApplication = entity;
        $scope.load = function (id) {
            APScaleApplication.get({id: id}, function(result) {
                $scope.aPScaleApplication = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:aPScaleApplicationUpdate', function(event, result) {
            $scope.aPScaleApplication = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
