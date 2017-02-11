'use strict';

angular.module('stepApp')
	.controller('EduLevelDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'EduLevel',
	 function($scope, $rootScope, $modalInstance, entity, EduLevel) {

        $scope.eduLevel = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            EduLevel.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.eduLevel.deleted');
                });
        };

    }]);
