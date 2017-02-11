'use strict';

angular.module('stepApp')
	.controller('InformationCorrectionDeleteController', function($scope, $modalInstance, entity, InformationCorrection) {

        $scope.informationCorrection = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InformationCorrection.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });