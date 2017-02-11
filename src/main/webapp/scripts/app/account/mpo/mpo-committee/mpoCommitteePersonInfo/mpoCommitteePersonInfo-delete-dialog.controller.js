'use strict';

angular.module('stepApp')
	.controller('MpoCommitteePersonInfoDeleteController',
	 ['$scope', '$modalInstance', 'entity', 'MpoCommitteePersonInfo',
	 function($scope, $modalInstance, entity, MpoCommitteePersonInfo) {

        $scope.mpoCommitteePersonInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            MpoCommitteePersonInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
