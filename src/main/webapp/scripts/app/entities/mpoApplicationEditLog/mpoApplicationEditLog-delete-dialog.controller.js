'use strict';

angular.module('stepApp')
	.controller('MpoApplicationEditLogDeleteController',
    ['$scope','$modalInstance','entity','MpoApplicationEditLog',
    function($scope, $modalInstance, entity, MpoApplicationEditLog) {

        $scope.mpoApplicationEditLog = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            MpoApplicationEditLog.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
