'use strict';

angular.module('stepApp')
    .controller('PgmsElpcDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'PgmsElpc', 'HrEmployeeInfo',
    function ($scope, $rootScope, $stateParams, entity, PgmsElpc, HrEmployeeInfo) {
        $scope.pgmsElpc = entity;
        $scope.load = function (id) {
            PgmsElpc.get({id: id}, function(result) {
                $scope.pgmsElpc = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:pgmsElpcUpdate', function(event, result) {
            $scope.pgmsElpc = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
