'use strict';

angular.module('stepApp')
    .controller('HrPayScaleSetupDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'HrPayScaleSetup', 'HrGradeSetup',
    function ($scope, $rootScope, $stateParams, entity, HrPayScaleSetup, HrGradeSetup) {
        $scope.hrPayScaleSetup = entity;
        $scope.load = function (id) {
            HrPayScaleSetup.get({id: id}, function(result) {
                $scope.hrPayScaleSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrPayScaleSetupUpdate', function(event, result) {
            $scope.hrPayScaleSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
