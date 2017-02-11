'use strict';

angular.module('stepApp')
	.controller('HrProjectInfoDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'HrProjectInfo',
	function($scope, $rootScope, $modalInstance, entity, HrProjectInfo) {

        $scope.hrProjectInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrProjectInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.HrProjectInfo.deleted');
        };

    }]);
