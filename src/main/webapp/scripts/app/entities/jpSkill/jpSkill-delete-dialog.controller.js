'use strict';

angular.module('stepApp')
	.controller('JpSkillDeleteController',
	 ['$scope', '$rootScope', '$modalInstance', 'entity', 'JpSkill',
	 function($scope, $rootScope, $modalInstance, entity, JpSkill) {

        $scope.jpSkill = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            JpSkill.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.jpSkill.deleted');
                });
        };

    }]);
