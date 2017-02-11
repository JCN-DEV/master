'use strict';

angular.module('stepApp')
	.controller('HrEmpPublicationInfoDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'HrEmpPublicationInfo',
	function($scope, $rootScope, $modalInstance, entity, HrEmpPublicationInfo) {

        $scope.hrEmpPublicationInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrEmpPublicationInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrEmpPublicationInfo.deleted');
                });
        };

    }]);
