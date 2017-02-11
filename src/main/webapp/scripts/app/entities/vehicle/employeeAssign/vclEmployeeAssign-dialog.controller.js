'use strict';

angular.module('stepApp').controller('VclEmployeeAssignDialogController',
    ['$scope', '$state', '$stateParams', 'entity', 'VclEmployeeAssign', 'VclVehicle', 'Employee','DateUtils','User','Principal',
        function($scope, $state, $stateParams, entity, VclEmployeeAssign, VclVehicle, Employee,DateUtils, User, Principal) {

        $scope.vclEmployeeAssign = entity;
        $scope.vclvehicles = VclVehicle.query();
        $scope.employees = Employee.query();
        $scope.load = function(id) {
            VclEmployeeAssign.get({id : id}, function(result) {
                $scope.vclEmployeeAssign = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:vclEmployeeAssignUpdate', result);
            $scope.isSaving = false;
            $state.go('vclEmployeeAssign');
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
                    $scope.vclEmployeeAssign.updateBy = result.id;
                    $scope.vclEmployeeAssign.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                    if ($scope.vclEmployeeAssign.id != null)
                    {
                        VclEmployeeAssign.update($scope.vclEmployeeAssign, onSaveSuccess, onSaveError);
                    }
                    else
                    {
                        $scope.vclEmployeeAssign.createBy = result.id;
                        $scope.vclEmployeeAssign.createDate = DateUtils.convertLocaleDateToServer(new Date());
                        VclEmployeeAssign.save($scope.vclEmployeeAssign, onSaveSuccess, onSaveError);
                    }
                });
            });
        };

        $scope.clear = function() {

        };
}]);
