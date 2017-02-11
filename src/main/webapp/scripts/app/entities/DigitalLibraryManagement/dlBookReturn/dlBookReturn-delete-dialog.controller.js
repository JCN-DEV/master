'use strict';

angular.module('stepApp')
	.controller('DlBookReturnDeleteController',
	['$scope','$rootScope','$state', '$modalInstance', 'entity', 'DlBookReturn',
	function($scope,$rootScope,$state, $modalInstance, entity, DlBookReturn) {

        $scope.dlBookReturn = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            $state.go('libraryInfo.dlBookReturn', null, { reload: true });
            DlBookReturn.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.dlBookReturn.deleted');
                });

        };

    }]);
