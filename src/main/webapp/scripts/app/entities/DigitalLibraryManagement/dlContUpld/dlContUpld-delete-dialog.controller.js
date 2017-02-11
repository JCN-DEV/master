'use strict';

angular.module('stepApp')
	.controller('DlContUpldDeleteController',
	['$scope','$state','$stateParams', '$rootScope', '$modalInstance', 'entity', 'DlContUpld',
	function($scope,$state,$stateParams, $rootScope, $modalInstance, entity, DlContUpld) {

        $scope.dlContUpld = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            $state.go('libraryInfo.dlContUpld', null, { reload: true });
            DlContUpld.delete({id: id},
                function () {
                    $modalInstance.close(true);
                     $rootScope.setErrorMessage('stepApp.dlContUpld.deleted');

                });

        };


    }]);
