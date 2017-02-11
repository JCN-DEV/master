'use strict';

angular.module('stepApp')
    .controller('AllowanceSetupDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'AllowanceSetup',
    function ($scope, $rootScope, $stateParams, entity, AllowanceSetup) {
        $scope.allowanceSetup = entity;
        $scope.load = function (id) {
            AllowanceSetup.get({id: id}, function(result) {
                $scope.allowanceSetup = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:allowanceSetupUpdate', function(event, result) {
            $scope.allowanceSetup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
