'use strict';

angular.module('stepApp')
	.controller('PrlEmpGenSalDetailInfoDeleteController', function($scope, $rootScope, $modalInstance, entity, PrlEmpGenSalDetailInfo) {

        $scope.prlEmpGenSalDetailInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrlEmpGenSalDetailInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.prlEmpGenSalDetailInfo.deleted');
                });
        };

    });
