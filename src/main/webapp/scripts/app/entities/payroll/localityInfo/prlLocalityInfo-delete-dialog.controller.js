'use strict';

angular.module('stepApp')
	.controller('PrlLocalityInfoDeleteController', function($scope, $rootScope, $modalInstance, entity, PrlLocalityInfo) {

        $scope.prlLocalityInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            PrlLocalityInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.prlLocalityInfo.deleted');
                });
        };

    });
