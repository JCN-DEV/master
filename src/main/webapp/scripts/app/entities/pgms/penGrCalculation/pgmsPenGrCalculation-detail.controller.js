'use strict';

angular.module('stepApp')
    .controller('PgmsPenGrCalculationDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'PgmsPenGrCalculation', 'HrGradeSetup', 'HrPayScaleSetup', 'PgmsPenGrRate',
    function ($scope, $rootScope, $stateParams, entity, PgmsPenGrCalculation, HrGradeSetup, HrPayScaleSetup, PgmsPenGrRate) {
        $scope.pgmsPenGrCalculation = entity;
        $scope.load = function (id) {
            PgmsPenGrCalculation.get({id: id}, function(result) {
                $scope.pgmsPenGrCalculation = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pgmsPenGrCalculationUpdate', function(event, result) {
            $scope.pgmsPenGrCalculation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
