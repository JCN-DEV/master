'use strict';

angular.module('stepApp')
	.controller('InstituteGovernBodyDeleteController',
    ['$scope', '$modalInstance', 'entity', 'InstituteGovernBody',
    function($scope, $modalInstance, entity, InstituteGovernBody) {

        $scope.instituteGovernBody = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstituteGovernBody.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
