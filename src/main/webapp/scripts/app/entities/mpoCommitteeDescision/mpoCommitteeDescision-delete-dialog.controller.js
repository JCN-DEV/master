'use strict';

angular.module('stepApp')
	.controller('MpoCommitteeDescisionDeleteController',
    ['$scope','$modalInstance','entity','MpoCommitteeDescision',
    function($scope, $modalInstance, entity, MpoCommitteeDescision) {

        $scope.mpoCommitteeDescision = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            MpoCommitteeDescision.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
