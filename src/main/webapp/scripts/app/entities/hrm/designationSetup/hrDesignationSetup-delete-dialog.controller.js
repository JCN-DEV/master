'use strict';

angular.module('stepApp')
	.controller('HrDesignationSetupDeleteController',
	 ['$scope', '$rootScope', '$modalInstance', 'entity', 'HrDesignationSetup',
	 function($scope, $rootScope, $modalInstance, entity, HrDesignationSetup) {

        $scope.hrDesignationSetup = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrDesignationSetup.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrDesignationSetup.deleted');
                });
        };

    }]);
