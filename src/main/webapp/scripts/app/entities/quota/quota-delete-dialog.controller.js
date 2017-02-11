'use strict';

angular.module('stepApp')
	.controller('QuotaDeleteController',
   [ '$scope','$modalInstance','entity','Quota',
    function($scope, $modalInstance, entity, Quota) {

        $scope.quota = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Quota.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
