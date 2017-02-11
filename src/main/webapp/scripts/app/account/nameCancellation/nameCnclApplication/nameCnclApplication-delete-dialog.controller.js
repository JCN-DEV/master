'use strict';

angular.module('stepApp')
	.controller('NameCnclApplicationDeleteController',
	 ['$scope', '$modalInstance', 'entity', 'NameCnclApplication',
	 function($scope, $modalInstance, entity, NameCnclApplication) {

        $scope.nameCnclApplication = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            NameCnclApplication.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
