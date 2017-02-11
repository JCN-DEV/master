'use strict';

angular.module('stepApp').controller('VclRequisitionDialogController',
    ['$scope', '$state', '$stateParams', 'entity', 'VclRequisition', 'User','DateUtils','Principal',
        function($scope, $state, $stateParams, entity, VclRequisition, User, DateUtils,Principal) {

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
            $state.go('vclRequisition');
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
                    $scope.vclRequisition.comments = "";
                    $scope.vclRequisition.user = result;
                    //$scope.vclRequisition.activeStatus = true;
                    $scope.vclRequisition.updateBy = result.id;
                    $scope.vclRequisition.requisitionStatus = "Open";
                    $scope.vclRequisition.activeStatus = true;
                    $scope.vclRequisition.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                    if ($scope.vclRequisition.id != null)
                    {
                        VclRequisition.update($scope.vclRequisition, onSaveSuccess, onSaveError);
                    }
                    else
                    {
                        $scope.vclRequisition.createBy = result.id;
                        $scope.vclRequisition.createDate = DateUtils.convertLocaleDateToServer(new Date());
                        VclRequisition.save($scope.vclRequisition, onSaveSuccess, onSaveError);
                    }
                });
            });
        };

        $scope.clear = function() {

        };
}]);
