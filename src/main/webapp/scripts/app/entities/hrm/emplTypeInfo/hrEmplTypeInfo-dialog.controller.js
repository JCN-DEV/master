'use strict';

angular.module('stepApp').controller('HrEmplTypeInfoDialogController',
    ['$scope','$rootScope', '$stateParams', '$state', 'entity', 'HrEmplTypeInfo','Principal','User','DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, HrEmplTypeInfo, Principal, User, DateUtils) {

        $scope.hrEmplTypeInfo = entity;
        $scope.load = function(id) {
            HrEmplTypeInfo.get({id : id}, function(result) {
                $scope.hrEmplTypeInfo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrEmplTypeInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrEmplTypeInfo');
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
                    $scope.hrEmplTypeInfo.updateBy = result.id;
                    $scope.hrEmplTypeInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                    if ($scope.hrEmplTypeInfo.id != null)
                    {
                        HrEmplTypeInfo.update($scope.hrEmplTypeInfo, onSaveSuccess, onSaveError);
                        $rootScope.setWarningMessage('stepApp.hrEmplTypeInfo.updated');
                    }
                    else
                    {
                        $scope.hrEmplTypeInfo.createBy = result.id;
                        $scope.hrEmplTypeInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                        HrEmplTypeInfo.save($scope.hrEmplTypeInfo, onSaveSuccess, onSaveError);
                        $rootScope.setSuccessMessage('stepApp.hrEmplTypeInfo.created');
                    }
                });
            });
        };

        $scope.clear = function() {
        };
}]);
