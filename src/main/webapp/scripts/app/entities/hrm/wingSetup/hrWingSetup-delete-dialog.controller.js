'use strict';

angular.module('stepApp')
	.controller('HrWingSetupDeleteController', function($scope, $modalInstance, entity, HrWingSetup) {

        $scope.hrWingSetup = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrWingSetup.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });