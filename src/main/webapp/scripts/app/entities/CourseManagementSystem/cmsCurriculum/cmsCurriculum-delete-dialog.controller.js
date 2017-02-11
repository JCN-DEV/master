'use strict';

angular.module('stepApp')
	.controller('CmsCurriculumDeleteController',
        ['$scope', '$modalInstance', 'entity', 'CmsCurriculum',
        function($scope, $modalInstance, entity, CmsCurriculum) {

        $scope.cmsCurriculum = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CmsCurriculum.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
