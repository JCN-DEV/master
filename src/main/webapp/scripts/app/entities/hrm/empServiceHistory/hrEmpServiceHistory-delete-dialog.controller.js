'use strict';

angular.module('stepApp')
	.controller('HrEmpServiceHistoryDeleteController',
	 ['$scope', '$rootScope', '$uibModalInstance', 'entity', 'HrEmpServiceHistory',
	 function($scope, $rootScope, $uibModalInstance, entity, HrEmpServiceHistory) {

        $scope.hrEmpServiceHistory = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpServiceHistory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.HrEmpServiceHistory.deleted');
        };

    }]);
