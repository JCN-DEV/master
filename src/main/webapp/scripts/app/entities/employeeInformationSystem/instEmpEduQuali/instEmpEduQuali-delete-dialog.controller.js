'use strict';

angular.module('stepApp')
	.controller('InstEmpEduQualiDeleteController',
	['$scope', '$modalInstance', 'entity', 'InstEmpEduQuali',
	function($scope, $modalInstance, entity, InstEmpEduQuali) {

        $scope.instEmpEduQuali = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstEmpEduQuali.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.instEmployee.deleted');
        };

    }]);
