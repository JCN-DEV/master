'use strict';

angular.module('stepApp')
	.controller('InformationCorrectionStatusLogDeleteController', function($scope, $modalInstance, entity, InformationCorrectionStatusLog) {

        $scope.informationCorrectionStatusLog = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InformationCorrectionStatusLog.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });