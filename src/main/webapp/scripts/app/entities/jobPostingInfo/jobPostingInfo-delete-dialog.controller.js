'use strict';

angular.module('stepApp')
	.controller('JobPostingInfoDeleteController',
    ['$scope', '$modalInstance', 'entity', 'JobPostingInfo',
    function($scope, $modalInstance, entity, JobPostingInfo) {

        $scope.jobPostingInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            JobPostingInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
