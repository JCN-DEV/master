'use strict';

angular.module('stepApp')
    .controller('HrDepartmentalProceedingDetailController',
     ['$scope', '$rootScope', '$stateParams', 'DataUtils', 'entity', 'HrDepartmentalProceeding', 'HrEmployeeInfo',
     function ($scope, $rootScope, $stateParams, DataUtils, entity, HrDepartmentalProceeding, HrEmployeeInfo) {
        $scope.hrDepartmentalProceeding = entity;
        $scope.load = function (id) {
            HrDepartmentalProceeding.get({id: id}, function(result) {
                $scope.hrDepartmentalProceeding = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:hrDepartmentalProceedingUpdate', function(event, result) {
            $scope.hrDepartmentalProceeding = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    }]);
