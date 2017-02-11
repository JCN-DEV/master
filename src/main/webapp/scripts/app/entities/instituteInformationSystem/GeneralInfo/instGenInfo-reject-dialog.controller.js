'use strict';

angular.module('stepApp')
	.controller('InstGenInfoRejectController',
    ['$scope', '$modalInstance', 'Institute', '$state', 'entity', 'InstGenInfo','$stateParams',
    function($scope, $modalInstance, Institute, $state, entity, InstGenInfo,$stateParams) {
       //$scope.instGenInfo = entity;
        console.log($stateParams.id+' :id');
        InstGenInfo.get({id:$stateParams.id}, function(result){
            $scope.instGenInfo = result;
            console.log($scope.instGenInfo);
        } );

        var onSaveSuccess = function (result) {
            //$scope.$emit('stepApp:instituteUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('instituteInfo.pendingInstituteList',{},{reload:true});
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.clear = function() {
            $modalInstance.close();
        };
        $scope.confirmReject = function () {
            $scope.isSaving = true;
            $scope.instGenInfo.status = -1;
            InstGenInfo.update($scope.instGenInfo, onSaveSuccess, onSaveError);

        };



    }]);
