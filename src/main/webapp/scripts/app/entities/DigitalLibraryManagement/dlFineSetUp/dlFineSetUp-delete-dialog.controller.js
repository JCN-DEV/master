'use strict';

angular.module('stepApp')
	.controller('DlFineSetUpDeleteController', function($scope, $rootScope, $modalInstance, entity, DlFineSetUp) {

        $scope.dlFineSetUp = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DlFineSetUp.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.DlFineSetUp.deleted');
                });
        };

    });
