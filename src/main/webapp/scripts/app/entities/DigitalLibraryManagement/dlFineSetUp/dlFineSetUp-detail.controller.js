'use strict';

angular.module('stepApp')
    .controller('DlFineSetUpDetailController', function ($scope, $rootScope, $stateParams, entity, DlFineSetUp, DlContCatSet) {
        $scope.dlFineSetUp = entity;
        $scope.load = function (id) {
            DlFineSetUp.get({id: id}, function(result) {
                $scope.dlFineSetUp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:dlFineSetUpUpdate', function(event, result) {
            $scope.dlFineSetUp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
