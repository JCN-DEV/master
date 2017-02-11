'use strict';

angular.module('stepApp')
	.controller('IisCurriculumInfoTempDeleteController', function($scope, $modalInstance, entity, IisCurriculumInfoTemp) {

        $scope.iisCurriculumInfoTemp = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            IisCurriculumInfoTemp.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });