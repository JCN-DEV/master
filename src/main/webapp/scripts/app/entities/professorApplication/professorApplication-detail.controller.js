'use strict';

angular.module('stepApp')
    .controller('ProfessorApplicationDetailController',
    ['$scope', '$rootScope', '$stateParams', 'entity', 'ProfessorApplication', 'InstEmployee',
    function ($scope, $rootScope, $stateParams, entity, ProfessorApplication, InstEmployee) {
        $scope.professorApplication = entity;
        $scope.load = function (id) {
            ProfessorApplication.get({id: id}, function(result) {
                $scope.professorApplication = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:professorApplicationUpdate', function(event, result) {
            $scope.professorApplication = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
