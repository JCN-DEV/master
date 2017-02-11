'use strict';

angular.module('stepApp')
	.controller('CmsSemesterDeleteController',
        ['$scope', '$modalInstance', 'entity', 'CmsSemester',
        function($scope, $modalInstance, entity, CmsSemester) {

        $scope.cmsSemester = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CmsSemester.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
