'use strict';

angular.module('stepApp')
	.controller('HrDesignationHeadSetupDeleteController', function($scope, $rootScope, $modalInstance, entity, HrDesignationHeadSetup) {

        $scope.hrDesignationHeadSetup = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrDesignationHeadSetup.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.hrDesignationHeadSetup.deleted');
        };

    });
