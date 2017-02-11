'use strict';

angular.module('stepApp')
	.controller('DlBookReturnDeleteController',
	['$scope', '$modalInstance', 'entity', 'DlBookReturn',
	 function($scope, $modalInstance, entity, DlBookReturn) {

        $scope.dlBookReturn = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DlBookReturn.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
