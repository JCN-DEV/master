'use strict';

angular.module('stepApp')
	.controller('InstituteInfraInfoDeleteController',
    ['$scope', '$modalInstance', 'entity', 'InstituteInfraInfo',
    function($scope, $modalInstance, entity, InstituteInfraInfo) {

        $scope.instituteInfraInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstituteInfraInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
