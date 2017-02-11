'use strict';

angular.module('stepApp').controller('VclVehicleDriverAssignDialogController',
    ['$scope', '$state', '$stateParams', 'entity', 'VclVehicleDriverAssign', 'VclVehicle', 'VclDriver','DateUtils','User','Principal',
        function($scope, $state, $stateParams, entity, VclVehicleDriverAssign, VclVehicle, VclDriver,DateUtils, User, Principal) {

        $scope.vclVehicleDriverAssign = entity;
        $scope.vclvehicles = VclVehicle.query();
        $scope.vcldrivers = VclDriver.query();
        $scope.load = function(id) {
            VclVehicleDriverAssign.get({id : id}, function(result) {
                $scope.vclVehicleDriverAssign = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:vclVehicleDriverAssignUpdate', result);
            $scope.isSaving = false;
            $state.go('vclVehicleDriverAssign');
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
                    $scope.isSaving = true;
                    $scope.vclVehicleDriverAssign.updateBy = result.id;
                    $scope.vclVehicleDriverAssign.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                    if ($scope.vclVehicleDriverAssign.id != null)
                    {
                        VclVehicleDriverAssign.update($scope.vclVehicleDriverAssign, onSaveSuccess, onSaveError);
                    }
                    else
                    {
                        $scope.vclVehicleDriverAssign.createBy = result.id;
                        $scope.vclVehicleDriverAssign.createDate = DateUtils.convertLocaleDateToServer(new Date());
                        VclVehicleDriverAssign.save($scope.vclVehicleDriverAssign, onSaveSuccess, onSaveError);
                    }
                });
            });
        };

        $scope.clear = function() {

        };
}]);
