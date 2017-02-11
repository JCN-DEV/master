'use strict';

angular.module('stepApp')
	.controller('DlContSCatSetDeleteController',
	['$scope','$state', '$rootScope','$modalInstance', 'entity', 'DlContSCatSet',
	 function($scope,$state, $rootScope, $modalInstance, entity, DlContSCatSet) {

        $scope.dlContSCatSet = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            $state.go('libraryInfo.dlContSCatSet', null, { reload: true });
            DlContSCatSet.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.dlContSCatSet.deleted');

                });

        };


    }]);
