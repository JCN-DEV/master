'use strict';

angular.module('stepApp')
	.controller('PrlAllowDeductInfoDeleteController', function($scope, $rootScope, $modalInstance, entity, PrlAllowDeductInfo) {

        $scope.prlAllowDeductInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrlAllowDeductInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.prlAllowDeductInfo.deleted');
                });
        };

    });
