'use strict';

angular.module('stepApp')
	.controller('MpoEducationDeleteController',
	['$scope', '$modalInstance', 'entity', 'ApplicantEducation',
	function($scope, $modalInstance, entity, ApplicantEducation) {

        $scope.applicantEducation = entity;

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.confirmDelete = function (educationId) {
            ApplicantEducation.delete({id: educationId},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
