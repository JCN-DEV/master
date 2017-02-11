'use strict';

angular.module('stepApp')
	.controller('DlContUpldApproveController',
	['$scope','$state', '$rootScope', '$modalInstance', 'entity', 'DlContUpld',
	function($scope,$state, $rootScope, $modalInstance, entity, DlContUpld) {

        $scope.dlContUpld = entity;
/*
       DlContUpld.get({id: $stateParams.id}, function (result) {
*/

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmApprove = function () {
             $scope.dlContUpld.status = 4;
             DlContUpld.update($scope.dlContUpld, onSaveSuccess);

        };
        $scope.confirmApproveByAdmin = function () {
             $scope.dlContUpld.status = 4;
             DlContUpld.update($scope.dlContUpld, onSaveSuccess);

        };


             var onSaveSuccess = function (result) {
                        $modalInstance.close(true);
                        $state.go('libraryInfo.dlContUpldByUser', null, { reload: true });

                    };

    }]);
