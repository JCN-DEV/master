'use strict';

angular.module('stepApp')
	.controller('CmsSubAssignDeleteController',
	['$scope', '$modalInstance', 'entity', 'CmsSubAssign',
	function($scope, $modalInstance, entity, CmsSubAssign) {

        $scope.cmsSubAssign = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CmsSubAssign.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
