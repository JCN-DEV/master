'use strict';

angular.module('stepApp')
    .controller('DlContTypeSetDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'DlContTypeSet',
    function ($scope, $rootScope, $stateParams, entity, DlContTypeSet) {
        $scope.dlContTypeSet = entity;
        $scope.load = function (id) {
            DlContTypeSet.get({id: id}, function(result) {
                $scope.dlContTypeSet = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:dlContTypeSetUpdate', function(event, result) {
            $scope.dlContTypeSet = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
