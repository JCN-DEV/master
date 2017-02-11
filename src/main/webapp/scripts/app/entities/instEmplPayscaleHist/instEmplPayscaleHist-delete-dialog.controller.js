'use strict';

angular.module('stepApp')
	.controller('InstEmplPayscaleHistDeleteController',
	['$scope','$modalInstance','entity','InstEmplPayscaleHist',
	function($scope, $modalInstance, entity, InstEmplPayscaleHist) {

        $scope.instEmplPayscaleHist = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstEmplPayscaleHist.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
