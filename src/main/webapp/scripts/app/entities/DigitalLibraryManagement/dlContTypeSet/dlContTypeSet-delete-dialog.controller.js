'use strict';

angular.module('stepApp')
	.controller('DlContTypeSetDeleteController',
	['$scope','$state', '$rootScope','$modalInstance', 'entity', 'DlContTypeSet',
	function($scope,$state, $rootScope, $modalInstance, entity, DlContTypeSet) {

        $scope.dlContTypeSet = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            $state.go('libraryInfo.dlContTypeSet', null, { reload: true });
            DlContTypeSet.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.dlContTypeSet.deleted');

                });

        };

    }]);
