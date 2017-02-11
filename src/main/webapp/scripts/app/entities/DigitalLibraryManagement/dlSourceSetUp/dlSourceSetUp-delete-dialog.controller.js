'use strict';

angular.module('stepApp')
	.controller('DlSourceSetUpDeleteController', function($scope, $rootScope, $modalInstance, entity, DlSourceSetUp) {

        $scope.dlSourceSetUp = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DlSourceSetUp.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.dlSourceSetUp.deleted');
                });
        };

    });
