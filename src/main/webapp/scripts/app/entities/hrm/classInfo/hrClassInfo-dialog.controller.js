'use strict';

angular.module('stepApp').controller('HrClassInfoDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'HrClassInfo','Principal','User','DateUtils',
        function($scope, $rootScope,  $stateParams, $state, entity, HrClassInfo, Principal, User, DateUtils) {

        $scope.hrClassInfo = entity;
        $scope.load = function(id)
        {
            HrClassInfo.get({id : id}, function(result) {
                $scope.hrClassInfo = result;
            });
        };

        var onSaveSuccess = function (result)
        {
            $scope.$emit('stepApp:hrClassInfoUpdate', result);
            $scope.isSaving = false;
            $state.go('hrClassInfo');
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
                    $scope.hrClassInfo.updateBy = result.id;
                    $scope.hrClassInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                    if ($scope.hrClassInfo.id != null)
                    {
                        HrClassInfo.update($scope.hrClassInfo, onSaveSuccess, onSaveError);
                        $rootScope.setWarningMessage('stepApp.hrClassInfo.updated');
                    }
                    else
                    {
                        $scope.hrClassInfo.createBy = result.id;
                        $scope.hrClassInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                        HrClassInfo.save($scope.hrClassInfo, onSaveSuccess, onSaveError);
                        $rootScope.setSuccessMessage('stepApp.hrClassInfo.created');
                    }
                });
            });
        };

        $scope.clear = function() {

        };
}]);
