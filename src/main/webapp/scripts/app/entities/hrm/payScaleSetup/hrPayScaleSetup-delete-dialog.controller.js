'use strict';

angular.module('stepApp')
	.controller('HrPayScaleSetupDeleteController',
	 ['$scope', '$rootScope', '$modalInstance', 'entity', 'HrPayScaleSetup',
	 function($scope, $rootScope, $modalInstance, entity, HrPayScaleSetup) {

        $scope.hrPayScaleSetup = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrPayScaleSetup.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrPayScaleSetup.deleted');
                });
        };

    }]);
