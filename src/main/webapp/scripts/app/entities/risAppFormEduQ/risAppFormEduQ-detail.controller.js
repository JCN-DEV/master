'use strict';

angular.module('stepApp')
    .controller('RisAppFormEduQDetailController', function ($scope, $rootScope, $stateParams, entity, RisAppFormEduQ, RisNewAppFormTwo) {
        $scope.risAppFormEduQ = entity;
        $scope.load = function (id) {
            RisAppFormEduQ.get({id: id}, function(result) {
                $scope.risAppFormEduQ = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:risAppFormEduQUpdate', function(event, result) {
            $scope.risAppFormEduQ = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
