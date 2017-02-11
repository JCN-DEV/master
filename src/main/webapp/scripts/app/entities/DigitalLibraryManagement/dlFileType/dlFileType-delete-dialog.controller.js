'use strict';

angular.module('stepApp')
	.controller('DlFileTypeDeleteController',
	['$scope','$state','$rootScope', '$modalInstance', 'entity', 'DlFileType',
	function($scope,$state,$rootScope, $modalInstance, entity, DlFileType) {

        $scope.dlFileType = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            $state.go('libraryInfo.dlFileType', null, { reload: true });
            DlFileType.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.dlFileType.deleted');


                });


        };

    }]);
