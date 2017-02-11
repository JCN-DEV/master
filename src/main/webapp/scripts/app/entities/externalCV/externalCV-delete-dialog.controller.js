'use strict';

angular.module('stepApp')
	.controller('ExternalCVDeleteController',
	['$scope', '$modalInstance', 'entity', 'ExternalCV',
	 function($scope, $modalInstance, entity, ExternalCV) {

        $scope.externalCV = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ExternalCV.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
