'use strict';

angular.module('stepApp')
	.controller('CareerInformationDeleteController',
	['$scope', '$modalInstance', 'entity', 'CareerInformation',
	 function($scope, $modalInstance, entity, CareerInformation) {

        $scope.careerInformation = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CareerInformation.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
