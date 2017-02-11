'use strict';

angular.module('stepApp')
	.controller('PrlLocalitySetInfoDeleteController', function($scope, $rootScope, $modalInstance, entity, PrlLocalitySetInfo) {

        $scope.prlLocalitySetInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrlLocalitySetInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.prlLocalitySetInfo.deleted');
                });
        };

    });
