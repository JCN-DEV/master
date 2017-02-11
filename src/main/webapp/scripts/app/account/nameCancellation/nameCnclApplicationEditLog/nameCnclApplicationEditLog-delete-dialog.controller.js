'use strict';

angular.module('stepApp')
	.controller('NameCnclApplicationEditLogDeleteController',
	['$scope', '$modalInstance', 'entity', 'NameCnclApplicationEditLog',
	function($scope, $modalInstance, entity, NameCnclApplicationEditLog) {

        $scope.nameCnclApplicationEditLog = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            NameCnclApplicationEditLog.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
