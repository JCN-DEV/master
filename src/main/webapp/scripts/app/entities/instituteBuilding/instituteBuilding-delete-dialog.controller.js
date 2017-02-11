'use strict';

angular.module('stepApp')
	.controller('InstituteBuildingDeleteController',
    ['$scope','$modalInstance','entity','InstituteBuilding',
    function($scope, $modalInstance, entity, InstituteBuilding) {

        $scope.instituteBuilding = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstituteBuilding.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
