'use strict';

angular.module('stepApp')
    .controller('ProfessorApplicationEditLogDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'ProfessorApplicationEditLog', 'PrincipleApplication',
    function ($scope, $rootScope, $stateParams, entity, ProfessorApplicationEditLog, PrincipleApplication) {
        $scope.professorApplicationEditLog = entity;
        $scope.load = function (id) {
            ProfessorApplicationEditLog.get({id: id}, function(result) {
                $scope.professorApplicationEditLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:professorApplicationEditLogUpdate', function(event, result) {
            $scope.professorApplicationEditLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
