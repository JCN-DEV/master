'use strict';

angular.module('stepApp').controller('AlmShiftSetupDialogController',
    ['$scope', '$rootScope','$stateParams', 'entity', 'AlmShiftSetup', 'Principal', '$state', 'User', 'DateUtils',
        function($scope, $rootScope,$stateParams, entity, AlmShiftSetup, Principal,  $state, User, DateUtils) {

            $scope.almShiftSetup = entity;
            $scope.load = function(id) {
                AlmShiftSetup.get({id : id}, function(result) {
                    $scope.almShiftSetup = result;
                });
            };


            var onSaveSuccess = function (result) {
                $scope.$emit('stepApp:almShiftSetupUpdate', result);
                $scope.isSaving = false;
                $state.go('almShiftSetup');
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
                        $scope.almShiftSetup.updateBy = result.id;
                        $scope.almShiftSetup.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                        if ($scope.almShiftSetup.id != null)
                        {
                            AlmShiftSetup.update($scope.almShiftSetup, onSaveSuccess, onSaveError);
                            $rootScope.setWarningMessage('stepApp.almShiftSetup.updated');
                        }
                        else
                        {
                            $scope.almShiftSetup.createBy = result.id;
                            $scope.almShiftSetup.createDate = DateUtils.convertLocaleDateToServer(new Date());
                            AlmShiftSetup.save($scope.almShiftSetup, onSaveSuccess, onSaveError);
                            $rootScope.setSuccessMessage('stepApp.almShiftSetup.created');
                        }
                    });
                });
            };

            $scope.clear = function() {
                $state.dismiss('cancel');
            };
        }]);
