'use strict';

angular.module('stepApp')
	.controller('HrEmpChildrenInfoDeleteController',
	['$scope','$rootScope', '$modalInstance', 'entity', 'HrEmpChildrenInfo',
	function($scope, $rootScope, $modalInstance, entity, HrEmpChildrenInfo) {

        $scope.hrEmpChildrenInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpChildrenInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.HrEmpChildrenInfo.deleted');
        };

    }]);
