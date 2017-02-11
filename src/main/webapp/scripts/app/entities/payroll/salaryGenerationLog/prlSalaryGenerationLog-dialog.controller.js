'use strict';

angular.module('stepApp').controller('PrlSalaryGenerationLogDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'PrlSalaryGenerationLog', 'PrlGeneratedSalaryInfo','User','Principal','DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, PrlSalaryGenerationLog, PrlGeneratedSalaryInfo, User, Principal, DateUtils) {

        $scope.prlSalaryGenerationLog = entity;
        $scope.prlgeneratedsalaryinfos = PrlGeneratedSalaryInfo.query();
        $scope.load = function(id) {
            PrlSalaryGenerationLog.get({id : id}, function(result) {
                $scope.prlSalaryGenerationLog = result;
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
            $scope.$emit('stepApp:prlSalaryGenerationLogUpdate', result);
            $scope.isSaving = false;
            $state.go('prlSalaryGenerationLog');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            $scope.prlSalaryGenerationLog.generateBy = $scope.loggedInUser.login;
            $scope.prlSalaryGenerationLog.generateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.prlSalaryGenerationLog.id != null) {
                PrlSalaryGenerationLog.update($scope.prlSalaryGenerationLog, onSaveSuccess, onSaveError);
                $rootScope.setWarningMessage('stepApp.prlSalaryGenerationLog.updated');
            } else {
                $scope.prlSalaryGenerationLog.createBy = $scope.loggedInUser.id;
                $scope.prlSalaryGenerationLog.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PrlSalaryGenerationLog.save($scope.prlSalaryGenerationLog, onSaveSuccess, onSaveError);
                $rootScope.setSuccessMessage('stepApp.prlSalaryGenerationLog.created');
            }
        };

        $scope.clear = function() {

        };
}]);
