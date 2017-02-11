'use strict';

angular.module('stepApp')
	.controller('IisCourseInfoTempDeleteController', function($scope, $modalInstance, entity, IisCourseInfoTemp) {

        $scope.iisCourseInfoTemp = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            IisCourseInfoTemp.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });