'use strict';

angular.module('stepApp')
	.controller('EduBoardDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'EduBoard',
	function($scope, $rootScope, $modalInstance, entity, EduBoard) {

        $scope.eduBoard = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            EduBoard.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.eduBoard.deleted');
                });
        };

    }]);
