'use strict';

angular.module('stepApp')
	.controller('InstituteEmpCountDeleteController',
    ['$scope', '$modalInstance', 'entity', 'InstituteEmpCount',
    function($scope, $modalInstance, entity, InstituteEmpCount) {

        $scope.instituteEmpCount = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstituteEmpCount.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
