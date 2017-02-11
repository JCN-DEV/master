'use strict';

angular.module('stepApp')
    .controller('InstGovernBodyTempDetailController', function ($scope, $rootScope, $stateParams, entity, InstGovernBodyTemp, Institute) {
        $scope.instGovernBodyTemp = entity;
        $scope.load = function (id) {
            InstGovernBodyTemp.get({id: id}, function(result) {
                $scope.instGovernBodyTemp = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:instGovernBodyTempUpdate', function(event, result) {
            $scope.instGovernBodyTemp = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
