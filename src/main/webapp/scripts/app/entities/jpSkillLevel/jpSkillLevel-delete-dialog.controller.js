'use strict';

angular.module('stepApp')
	.controller('JpSkillLevelDeleteController',
	 ['$scope', '$rootScope', '$modalInstance', 'entity', 'JpSkillLevel',
	 function($scope, $rootScope, $modalInstance, entity, JpSkillLevel) {

        $scope.jpSkillLevel = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            JpSkillLevel.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.jpSkillLevel.deleted');
                });
        };

    }]);
