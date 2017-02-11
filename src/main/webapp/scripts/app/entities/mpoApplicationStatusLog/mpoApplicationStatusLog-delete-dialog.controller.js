'use strict';

angular.module('stepApp')
	.controller('MpoApplicationStatusLogDeleteController',
    ['$scope','$modalInstance','entity','MpoApplicationStatusLog',
    function($scope, $modalInstance, entity, MpoApplicationStatusLog) {

        $scope.mpoApplicationStatusLog = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            MpoApplicationStatusLog.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
