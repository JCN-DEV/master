'use strict';

angular.module('stepApp')
	.controller('HrDepartmentalProceedingDeleteController',
	 ['$scope', '$rootScope', '$modalInstance', 'entity', 'HrDepartmentalProceeding',
	 function($scope, $rootScope, $modalInstance, entity, HrDepartmentalProceeding) {

        $scope.hrDepartmentalProceeding = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrDepartmentalProceeding.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.HrDepartmentalProceeding.deleted');
        };

    }]);
