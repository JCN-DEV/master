'use strict';

angular.module('stepApp')
    .controller('MpoSalaryFlowDetailController',
    ['$scope','$rootScope','$stateParams','entity','MpoSalaryFlow',
    function ($scope, $rootScope, $stateParams, entity, MpoSalaryFlow) {
        $scope.mpoSalaryFlow = entity;
        $scope.load = function (id) {
            MpoSalaryFlow.get({id: id}, function(result) {
                $scope.mpoSalaryFlow = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:mpoSalaryFlowUpdate', function(event, result) {
            $scope.mpoSalaryFlow = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
