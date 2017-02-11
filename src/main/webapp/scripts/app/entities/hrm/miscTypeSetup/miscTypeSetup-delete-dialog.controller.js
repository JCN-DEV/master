'use strict';

angular.module('stepApp')
	.controller('MiscTypeSetupDeleteController',
	['$scope','$rootScope','$modalInstance', 'entity', 'MiscTypeSetup',
	function($scope,$rootScope, $modalInstance, entity, MiscTypeSetup) {

        $scope.miscTypeSetup = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            MiscTypeSetup.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.miscTypeSetup.deleted');
                });
        };

    }]);
