'use strict';

angular.module('stepApp')
	.controller('HrEntertainmentBenefitDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'HrEntertainmentBenefit',
	function($scope, $rootScope, $modalInstance, entity, HrEntertainmentBenefit) {

        $scope.hrEntertainmentBenefit = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEntertainmentBenefit.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.HrEntertainmentBenefit.deleted');
        };

    }]);
