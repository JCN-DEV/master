'use strict';

angular.module('stepApp')
	.controller('QrcodeGenLogDeleteController',
	['$scope', '$modalInstance', 'entity', 'QrcodeGenLog',
	function($scope, $modalInstance, entity, QrcodeGenLog) {

        $scope.qrcodeGenLog = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            QrcodeGenLog.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
