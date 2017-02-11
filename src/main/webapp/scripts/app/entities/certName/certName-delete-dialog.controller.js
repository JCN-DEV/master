'use strict';

angular.module('stepApp')
	.controller('CertNameDeleteController',
	['$scope', '$modalInstance', 'entity', 'CertName',
	function($scope, $modalInstance, entity, CertName) {

        $scope.certName = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            CertName.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
