'use strict';

angular.module('stepApp').controller('VclVehicleDialogController',
    ['$scope', '$state','$stateParams' , 'entity', 'VclVehicle','DateUtils','User','Principal',
        function($scope, $state, $stateParams, entity, VclVehicle,DateUtils,User,Principal) {

            $scope.vclVehicle = entity;
            $scope.load = function(id) {
                VclVehicle.get({id : id}, function(result) {
                    $scope.vclVehicle = result;
                });
            };

            var onSaveSuccess = function (result) {
                $scope.$emit('stepApp:vclVehicleUpdate', result);
                $scope.isSaving = false;
                $state.go('vehiclemgt');
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
                        $scope.vclVehicle.updateBy = result.id;
                        $scope.vclVehicle.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                        if ($scope.vclVehicle.id != null)
                        {
                            VclVehicle.update($scope.vclVehicle, onSaveSuccess, onSaveError);
                        }
                        else
                        {
                            $scope.vclVehicle.createBy = result.id;
                            $scope.vclVehicle.createDate = DateUtils.convertLocaleDateToServer(new Date());
                            VclVehicle.save($scope.vclVehicle, onSaveSuccess, onSaveError);
                        }
                    });
                });
            };

            $scope.clear = function() {

            };
        }]);
