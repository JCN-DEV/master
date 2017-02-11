'use strict';

angular.module('stepApp')
	.controller('HrGradeSetupDeleteController',
	['$scope', '$rootScope', '$modalInstance', 'entity', 'HrGradeSetup',
	function($scope, $rootScope, $modalInstance, entity, HrGradeSetup)
    {
        $scope.hrGradeSetup = entity;
        $scope.clear = function()
        {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id)
        {
            HrGradeSetup.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.hrGradeSetup.deleted');

                });
        };
    }]);
