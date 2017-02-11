'use strict';

angular.module('stepApp')
	.controller('PrlOnetimeAllowInfoDeleteController', function($scope, $rootScope, $modalInstance, entity, PrlOnetimeAllowInfo) {

        $scope.prlOnetimeAllowInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrlOnetimeAllowInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.prlOnetimeAllowInfo.deleted');
                });
        };

    });
