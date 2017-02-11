'use strict';

angular.module('stepApp')
	.controller('InstituteFinancialInfoDeleteController',
    ['$scope','$modalInstance','entity','InstituteFinancialInfo',
    function($scope, $modalInstance, entity, InstituteFinancialInfo) {

        $scope.instituteFinancialInfo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            InstituteFinancialInfo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    }]);
