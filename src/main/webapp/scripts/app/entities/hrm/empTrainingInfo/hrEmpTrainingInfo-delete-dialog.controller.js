'use strict';

angular.module('stepApp')
	.controller('HrEmpTrainingInfoDeleteController',
	 ['$scope', '$rootScope', '$modalInstance', 'entity', 'HrEmpTrainingInfo',
	 function($scope, $rootScope, $modalInstance, entity, HrEmpTrainingInfo) {

        $scope.hrEmpTrainingInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpTrainingInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrEmpTrainingInfo.deleted');
                });
        };

    }]);
