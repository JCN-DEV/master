'use strict';

angular.module('stepApp').controller('VclRequisitionApproveController',
    ['$scope', '$state', '$stateParams', 'entity', 'VclRequisition', 'User','DateUtils','Principal','VclVehicle', 'VclDriver',
        function($scope, $state, $stateParams, entity, VclRequisition, User, DateUtils,Principal,VclVehicle, VclDriver) {

        $scope.vclvehicles = VclVehicle.query();
        $scope.vcldrivers = VclDriver.query();
        $scope.vclRequisition = entity;
        $scope.users = User.query();
        $scope.load = function(id) {
            VclRequisition.get({id : id}, function(result) {
                $scope.vclRequisition = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:vclRequisitionUpdate', result);
            $scope.isSaving = false;
            $state.go('vclRequisition.dashboard');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    //stepApp.requisitionStatus.Open
                    $scope.isSaving = true;
                    //$scope.vclRequisition.user = result;
                    //$scope.vclRequisition.activeStatus = true;
                    $scope.vclRequisition.requisitionStatus = "Approved";
                    $scope.vclRequisition.actionBy = result.id;
                    $scope.vclRequisition.actionDate = DateUtils.convertLocaleDateToServer(new Date());
                    $scope.vclRequisition.updateBy = result.id;
                    $scope.vclRequisition.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                    if ($scope.vclRequisition.id != null)
                    {
                        VclRequisition.update($scope.vclRequisition, onSaveSuccess, onSaveError);
                    }
                });
            });
        };

        $scope.clear = function() {

        };
}]);
