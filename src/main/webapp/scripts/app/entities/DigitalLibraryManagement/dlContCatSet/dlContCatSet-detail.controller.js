'use strict';

angular.module('stepApp')
    .controller('DlContCatSetDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'DlContCatSet', 'DlContTypeSet',
    function ($scope, $rootScope, $stateParams, entity, DlContCatSet, DlContTypeSet) {
        $scope.dlContCatSet = entity;
        $scope.load = function (id) {
            DlContCatSet.get({id: id}, function(result) {
                $scope.dlContCatSet = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:dlContCatSetUpdate', function(event, result) {
            $scope.dlContCatSet = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
