'use strict';

angular.module('stepApp')
	.controller('IisCurriculumInfoDeleteController',
    ['$scope', '$rootScope', '$modalInstance', 'entity', 'IisCurriculumInfo',
    function($scope, $rootScope, $modalInstance, entity, IisCurriculumInfo) {

        $scope.iisCurriculumInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            IisCurriculumInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
            $rootScope.setErrorMessage('stepApp.iisCurriculumInfo.deleted');
        };

    }]);
