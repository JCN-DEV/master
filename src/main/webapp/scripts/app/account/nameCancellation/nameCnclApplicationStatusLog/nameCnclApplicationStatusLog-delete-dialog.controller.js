'use strict';

angular.module('stepApp')
	.controller('NameCnclApplicationStatusLogDeleteController', function($scope, $modalInstance, entity, NameCnclApplicationStatusLog) {

        $scope.nameCnclApplicationStatusLog = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            NameCnclApplicationStatusLog.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });