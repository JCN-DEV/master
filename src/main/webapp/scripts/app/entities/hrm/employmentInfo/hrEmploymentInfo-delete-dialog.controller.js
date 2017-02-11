'use strict';

angular.module('stepApp')
	.controller('HrEmploymentInfoDeleteController',
	['$scope', '$rootScope', '$modalInstance','entity', 'HrEmploymentInfo',
	function($scope, $rootScope, $modalInstance, entity, HrEmploymentInfo) {

        $scope.hrEmploymentInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmploymentInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrEmploymentInfo.deleted');
                });
        };

    }]);
