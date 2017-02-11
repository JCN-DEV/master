'use strict';

angular.module('stepApp')
	.controller('DeoHistLogDeleteController', function($scope, $modalInstance, entity, DeoHistLog) {

        $scope.deoHistLog = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DeoHistLog.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });