'use strict';

angular.module('stepApp')
	.controller('DlBookEditionDeleteController', function($scope, $modalInstance, entity, DlBookEdition) {

        $scope.dlBookEdition = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DlBookEdition.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });