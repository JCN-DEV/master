'use strict';

angular.module('stepApp')
    .controller('NameCnclApplicationEditLogDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'NameCnclApplicationEditLog', 'NameCnclApplication',
    function ($scope, $rootScope, $stateParams, entity, NameCnclApplicationEditLog, NameCnclApplication) {
        $scope.nameCnclApplicationEditLog = entity;
        $scope.load = function (id) {
            NameCnclApplicationEditLog.get({id: id}, function(result) {
                $scope.nameCnclApplicationEditLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:nameCnclApplicationEditLogUpdate', function(event, result) {
            $scope.nameCnclApplicationEditLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
