'use strict';

angular.module('stepApp')
	.controller('InformationCorrectionEditLogDeleteController', function($scope, $modalInstance, entity, InformationCorrectionEditLog) {

        $scope.informationCorrectionEditLog = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InformationCorrectionEditLog.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });