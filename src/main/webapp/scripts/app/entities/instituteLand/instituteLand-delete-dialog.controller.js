'use strict';

angular.module('stepApp')
	.controller('InstituteLandDeleteController',
    ['$scope', '$modalInstance', 'entity', 'InstituteLand',
    function($scope, $modalInstance, entity, InstituteLand) {

        $scope.instituteLand = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstituteLand.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
