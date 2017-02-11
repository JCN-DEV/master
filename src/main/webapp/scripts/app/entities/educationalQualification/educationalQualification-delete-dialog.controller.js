'use strict';

angular.module('stepApp')
	.controller('EducationalQualificationDeleteController',
	['$scope', '$modalInstance', 'entity', 'EducationalQualification',
	function($scope, $modalInstance, entity, EducationalQualification) {

        $scope.educationalQualification = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            EducationalQualification.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
