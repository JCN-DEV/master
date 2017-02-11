'use strict';

angular.module('stepApp')
	.controller('PrlEmpGeneratedSalInfoDeleteController', function($scope, $rootScope, $modalInstance, entity, PrlEmpGeneratedSalInfo) {

        $scope.prlEmpGeneratedSalInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrlEmpGeneratedSalInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.prlEmpGeneratedSalInfo.deleted');
                });
        };

    });
