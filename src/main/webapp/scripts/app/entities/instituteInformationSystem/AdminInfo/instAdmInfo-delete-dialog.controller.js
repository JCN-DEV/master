'use strict';

angular.module('stepApp')
	.controller('InstAdmInfoDeleteController',
    ['$scope', '$rootScope', '$modalInstance', 'entity', 'InstAdmInfo',
    function($scope, $rootScope, $modalInstance, entity, InstAdmInfo) {

        $scope.instAdmInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstAdmInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.InstAdmInfo.deleted');
                });
        };

    }]);
