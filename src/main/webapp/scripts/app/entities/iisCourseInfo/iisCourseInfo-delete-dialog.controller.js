'use strict';

angular.module('stepApp')
	.controller('IisCourseInfoDeleteeeeeeeeeeController',
	['$scope', '$rootScope','$modalInstance','entity','IisCourseInfo',
	function($scope, $rootScope, $modalInstance, entity, IisCourseInfo) {

        $scope.iisCourseInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            IisCourseInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.IisCourseInfo.deleted');
        };

    }]);
