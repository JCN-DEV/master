'use strict';

angular.module('stepApp')
	.controller('TimeScaleApplicationDeleteController',
    ['$scope', '$modalInstance', 'entity', 'TimeScaleApplication',
    function($scope, $modalInstance, entity, TimeScaleApplication) {

        $scope.timeScaleApplication = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TimeScaleApplication.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
