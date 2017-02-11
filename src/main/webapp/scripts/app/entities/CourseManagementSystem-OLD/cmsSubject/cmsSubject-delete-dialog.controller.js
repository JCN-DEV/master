'use strict';

angular.module('stepApp')
	.controller('CmsSubjectDeleteController',
	['$scope', '$modalInstance', 'entity', 'CmsSubject',
	function($scope, $modalInstance, entity, CmsSubject) {

        $scope.cmsSubject = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CmsSubject.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
