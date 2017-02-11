'use strict';

angular.module('stepApp')
	.controller('ApplicantEducationDeleteController',
	['$scope', '$modalInstance', 'entity', 'ApplicantEducation',
	function($scope, $modalInstance, entity, ApplicantEducation) {

        $scope.applicantEducation = entity;

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.confirmDelete = function (id) {
            ApplicantEducation.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
