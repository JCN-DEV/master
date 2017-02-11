'use strict';

angular.module('stepApp')
	.controller('HrNomineeInfoDeleteController',
	 ['$scope', '$rootScope', '$modalInstance', 'entity', 'HrNomineeInfo',
	 function($scope, $rootScope, $modalInstance, entity, HrNomineeInfo) {

        $scope.hrNomineeInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            HrNomineeInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.HrNomineeInfo.deleted');
        };

    }]);
