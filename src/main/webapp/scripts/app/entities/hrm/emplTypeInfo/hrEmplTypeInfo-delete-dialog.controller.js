'use strict';

angular.module('stepApp')
	.controller('HrEmplTypeInfoDeleteController',
	 ['$scope', '$modalInstance', 'entity', 'HrEmplTypeInfo',
	 function($scope, $modalInstance, entity, HrEmplTypeInfo) {

        $scope.hrEmplTypeInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmplTypeInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
