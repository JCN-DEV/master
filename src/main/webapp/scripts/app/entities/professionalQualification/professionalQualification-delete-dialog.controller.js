'use strict';

angular.module('stepApp')
	.controller('ProfessionalQualificationDeleteController',
    ['$scope', '$modalInstance', 'entity', 'ProfessionalQualification',
    function($scope, $modalInstance, entity, ProfessionalQualification) {

        $scope.professionalQualification = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ProfessionalQualification.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
