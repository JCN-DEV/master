'use strict';

angular.module('stepApp').controller('SisQuotaDialogController',
    ['$scope', '$rootScope','$stateParams', 'entity', 'Principal','SisQuota', '$state', 'User','DateUtils',
        function($scope, $rootScope, $stateParams,  entity, Principal, SisQuota, $state, User, DateUtils) {

            $scope.sisQuota = entity;
            $scope.load = function(id) {
                SisQuota.get({id : id}, function(result) {
                    $scope.sisQuota = result;
                });
            };


            var onSaveSuccess = function (result) {
                $scope.$emit('stepApp:sisQuotaUpdate', result);
                $scope.isSaving = false;
                $state.go('sisQuota');
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
                        $scope.sisQuota.updateBy = result.id;
                        $scope.sisQuota.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                        if ($scope.sisQuota.id != null)
                        {
                            SisQuota.update($scope.sisQuota, onSaveSuccess, onSaveError);
                            $rootScope.setWarningMessage('stepApp.sisQuota.updated');
                        }
                        else
                        {
                            $scope.sisQuota.createBy = result.id;
                            $scope.sisQuota.createDate = DateUtils.convertLocaleDateToServer(new Date());
                            SisQuota.save($scope.sisQuota, onSaveSuccess, onSaveError);
                            $rootScope.setSuccessMessage('stepApp.sisQuota.created');
                        }
                    });
                });
            };


            $scope.clear = function() {
                $modalInstance.dismiss('cancel');
            };

        }]);

