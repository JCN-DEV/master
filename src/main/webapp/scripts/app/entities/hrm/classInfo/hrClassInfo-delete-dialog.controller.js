'use strict';

angular.module('stepApp')
	.controller('HrClassInfoDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'HrClassInfo',
	function($scope, $rootScope, $modalInstance, entity, HrClassInfo) {

        $scope.hrClassInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrClassInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrClassInfo.deleted');
                });
        };

    }]);
