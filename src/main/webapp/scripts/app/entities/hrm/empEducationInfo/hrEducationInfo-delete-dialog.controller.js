'use strict';

angular.module('stepApp')
	.controller('HrEducationInfoDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'HrEducationInfo',
	function($scope, $rootScope, $modalInstance, entity, HrEducationInfo) {

        $scope.hrEducationInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEducationInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrEducationInfo.deleted');
                });
        };

    }]);
