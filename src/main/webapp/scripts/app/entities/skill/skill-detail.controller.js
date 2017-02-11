'use strict';

angular.module('stepApp')
    .controller('SkillDetailController',

    ['$scope', '$rootScope', '$stateParams', 'entity', 'Skill', 'Employee', 'User',
    function ($scope, $rootScope, $stateParams, entity, Skill, Employee, User) {
        $scope.skill = entity;
        $scope.load = function (id) {
            Skill.get({id: id}, function(result) {
                $scope.skill = result;
            });
        };
        var unsubscribe = $rootScope.$on('stepApp:skillUpdate', function(event, result) {
            $scope.skill = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }]);
