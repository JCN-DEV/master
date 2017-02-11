'use strict';

angular.module('stepApp')
	.controller('CmsSyllabusDeleteController',
	['$scope', '$modalInstance', 'entity', 'CmsSyllabus',
	 function($scope, $modalInstance, entity, CmsSyllabus) {

        $scope.cmsSyllabus = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CmsSyllabus.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
