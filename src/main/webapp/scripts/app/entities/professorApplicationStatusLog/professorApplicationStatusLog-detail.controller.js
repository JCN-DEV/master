'use strict';

angular.module('stepApp')
    .controller('ProfessorApplicationStatusLogDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'ProfessorApplicationStatusLog', 'PrincipleApplication',
    function ($scope, $rootScope, $stateParams, entity, ProfessorApplicationStatusLog, PrincipleApplication) {
        $scope.professorApplicationStatusLog = entity;
        $scope.load = function (id) {
            ProfessorApplicationStatusLog.get({id: id}, function(result) {
                $scope.professorApplicationStatusLog = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:professorApplicationStatusLogUpdate', function(event, result) {
            $scope.professorApplicationStatusLog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
