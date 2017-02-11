'use strict';

angular.module('stepApp').controller('VclDriverDialogController',
    ['$scope', '$state','$stateParams', 'DataUtils', 'entity', 'VclDriver','DateUtils','User','Principal',
        function($scope, $state, $stateParams, DataUtils, entity, VclDriver,DateUtils, User, Principal)
        {

        $scope.vclDriver = entity;
        $scope.load = function(id) {
            VclDriver.get({id : id}, function(result) {
                $scope.vclDriver = result;
            });
        };

        $scope.filterNumericValue = function($event){
            if(isNaN(String.fromCharCode($event.keyCode))){
                $event.preventDefault();
            }
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:vclDriverUpdate', result);
            //$modalInstance.close(result);
            $scope.isSaving = false;
            $state.go('driver');
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
                    $scope.vclDriver.updateBy = result.id;
                    $scope.vclDriver.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                    if ($scope.vclDriver.id != null)
                    {
                        VclDriver.update($scope.vclDriver, onSaveSuccess, onSaveError);
                    }
                    else
                    {
                        $scope.vclDriver.createBy = result.id;
                        $scope.vclDriver.createDate = DateUtils.convertLocaleDateToServer(new Date());
                        VclDriver.save($scope.vclDriver, onSaveSuccess, onSaveError);
                    }
                });
            });

        };

        $scope.clear = function() {
            //$modalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setDriverPhoto = function ($file, vclDriver) {
            if ($file)
            {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        vclDriver.driverPhoto = base64Data;
                        vclDriver.driverPhotoContentType = $file.type;
                    });
                };
            }
        };
}]);
