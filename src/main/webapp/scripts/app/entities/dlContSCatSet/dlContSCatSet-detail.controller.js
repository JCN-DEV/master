'use strict';

angular.module('stepApp')
    .controller('DlContSCatSetDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'DlContSCatSet', 'DlContTypeSet', 'DlContCatSet',
    function ($scope, $rootScope, $stateParams, entity, DlContSCatSet, DlContTypeSet, DlContCatSet) {
        $scope.dlContSCatSet = entity;
        $scope.load = function (id) {
            DlContSCatSet.get({id: id}, function(result) {
                $scope.dlContSCatSet = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:dlContSCatSetUpdate', function(event, result) {
            $scope.dlContSCatSet = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
