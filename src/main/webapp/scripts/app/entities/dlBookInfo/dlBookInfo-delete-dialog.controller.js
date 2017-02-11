'use strict';

angular.module('stepApp')
	.controller('DlBookInfoDeleteController',
	['$scope', '$modalInstance', 'entity', 'DlBookInfo',
	 function($scope, $modalInstance, entity, DlBookInfo) {

        $scope.dlBookInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DlBookInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
