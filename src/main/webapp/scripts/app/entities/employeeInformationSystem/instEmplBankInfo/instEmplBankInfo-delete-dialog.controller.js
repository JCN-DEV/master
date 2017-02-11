'use strict';

angular.module('stepApp')
	.controller('InstEmplBankInfoDeleteController',
	['$scope', '$modalInstance', 'entity', 'InstEmplBankInfo',
	function($scope, $modalInstance, entity, InstEmplBankInfo) {

        $scope.instEmplBankInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstEmplBankInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
