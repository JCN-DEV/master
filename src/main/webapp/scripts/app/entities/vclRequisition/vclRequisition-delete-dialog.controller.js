'use strict';

angular.module('stepApp')
	.controller('VclRequisitionDeleteController',
    ['$scope', '$uibModalInstance', 'entity', 'VclRequisition',
    function($scope, $uibModalInstance, entity, VclRequisition) {

        $scope.vclRequisition = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            VclRequisition.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    }]);
