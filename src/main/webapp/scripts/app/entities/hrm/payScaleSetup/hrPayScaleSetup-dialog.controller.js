'use strict';

angular.module('stepApp').controller('HrPayScaleSetupDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'HrPayScaleSetup', 'HrGradeSetup','Principal','User','DateUtils','HrGazetteSetup',
        function($scope, $rootScope, $stateParams, $state, entity, HrPayScaleSetup, HrGradeSetup, Principal, User, DateUtils, HrGazetteSetup) {

        $scope.hrPayScaleSetup = entity;
        $scope.hrgradesetups = HrGradeSetup.query({id:'bystat', size: 500});
        $scope.hrgazettesetups = HrGazetteSetup.query({size:500});
        $scope.load = function(id)
        {
            HrPayScaleSetup.get({id : id}, function(result) {
                $scope.hrPayScaleSetup = result;
            });
        };

        var onSaveSuccess = function (result)
        {
            $scope.$emit('stepApp:hrPayScaleSetupUpdate', result);
            $scope.isSaving = false;
            $state.go('hrPayScaleSetup');
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
                    $scope.hrPayScaleSetup.updateBy = result.id;
                    $scope.hrPayScaleSetup.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                    if ($scope.hrPayScaleSetup.id != null)
                    {
                        HrPayScaleSetup.update($scope.hrPayScaleSetup, onSaveSuccess, onSaveError);
                        $rootScope.setWarningMessage('stepApp.hrPayScaleSetup.updated');
                    }
                    else
                    {
                        $scope.hrPayScaleSetup.createBy = result.id;
                        $scope.hrPayScaleSetup.createDate = DateUtils.convertLocaleDateToServer(new Date());
                        HrPayScaleSetup.save($scope.hrPayScaleSetup, onSaveSuccess, onSaveError);
                        $rootScope.setSuccessMessage('stepApp.hrPayScaleSetup.created');
                    }
                });
            });
        };

        $scope.clear = function() {
        };
}]);
