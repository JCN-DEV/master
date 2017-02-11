'use strict';

angular.module('stepApp')
	.controller('DlFileTypeDeleteController',
	['$scope', '$modalInstance', 'entity', 'DlFileType',
	 function($scope, $modalInstance, entity, DlFileType) {

        $scope.dlFileType = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DlFileType.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
