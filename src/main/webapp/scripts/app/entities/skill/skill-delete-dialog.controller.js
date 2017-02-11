'use strict';

angular.module('stepApp')
	.controller('SkillDeleteController',
    ['$scope', '$modalInstance', 'entity', 'Skill',
    function($scope, $modalInstance, entity, Skill) {

        $scope.skill = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Skill.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
