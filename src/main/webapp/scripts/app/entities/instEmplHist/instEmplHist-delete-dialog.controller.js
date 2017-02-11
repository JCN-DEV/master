'use strict';

angular.module('stepApp')
	.controller('InstEmplHistDeleteController',
	['$scope','$modalInstance','entity','InstEmplHist',
	function($scope, $modalInstance, entity, InstEmplHist) {

        $scope.instEmplHist = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstEmplHist.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
