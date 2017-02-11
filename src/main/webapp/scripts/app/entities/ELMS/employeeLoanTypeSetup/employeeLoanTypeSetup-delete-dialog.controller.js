'use strict';

angular.module('stepApp')
	.controller('EmployeeLoanTypeSetupDeleteController',
	['$scope','$state', '$rootScope', 'entity', 'EmployeeLoanTypeSetup',
	function($scope,$state, $rootScope, entity, EmployeeLoanTypeSetup) {

        $scope.employeeLoanTypeSetup = entity;
        $scope.clear = function() {
           // $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            $state.go('employeeLoanInfo.employeeLoanTypeSetup',{},{reload:true});
            EmployeeLoanTypeSetup.delete({id: id},
                function () {
                   //  $modalInstance.close(true);
                    $rootScope.setErrorMessage('stepApp.employeeLoanTypeSetup.deleted');

                });


        };

    }]);
