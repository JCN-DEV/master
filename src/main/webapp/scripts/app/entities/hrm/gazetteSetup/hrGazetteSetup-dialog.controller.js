'use strict';

angular.module('stepApp').controller('HrGazetteSetupDialogController',
    ['$scope','$rootScope', '$stateParams','$state', 'entity', 'HrGazetteSetup','User','Principal','DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, HrGazetteSetup, User, Principal, DateUtils) {

        $scope.hrGazetteSetup = entity;
        $scope.load = function(id) {
            HrGazetteSetup.get({id : id}, function(result) {
                $scope.hrGazetteSetup = result;
            });
        };

        $scope.loggedInUser =   {};
        $scope.getLoggedInUser = function ()
        {
            Principal.identity().then(function (account)
            {
                User.get({login: account.login}, function (result)
                {
                    $scope.loggedInUser = result;
                });
            });
        };
        $scope.getLoggedInUser();

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:hrGazetteSetupUpdate', result);
            $scope.isSaving = false;
            $state.go('hrGazetteSetup');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function ()
        {
            $scope.isSaving = true;
            $scope.hrGazetteSetup.updateBy = $scope.loggedInUser.id;
            $scope.hrGazetteSetup.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.hrGazetteSetup.id != null) {
                HrGazetteSetup.update($scope.hrGazetteSetup, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.hrGazetteSetup.updated');
            } else
            {
                $scope.hrGazetteSetup.createBy = $scope.loggedInUser.id;
                $scope.hrGazetteSetup.createDate = DateUtils.convertLocaleDateToServer(new Date());
                HrGazetteSetup.save($scope.hrGazetteSetup, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.hrGazetteSetup.created');
            }
        };

        $scope.clear = function() {
        };
}]);
