'use strict';

angular.module('stepApp').controller('PgmsPenRulesDialogController',
    ['$scope', '$rootScope', '$stateParams', '$state', 'entity', 'PgmsPenRules', 'User', 'Principal', 'DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, PgmsPenRules, User, Principal, DateUtils) {

        $scope.pgmsPenRules = entity;
        $scope.users = User.query({filter: 'pgmsPenRules-is-null'});

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

        $scope.load = function(id) {
            PgmsPenRules.get({id : id}, function(result) {
                $scope.pgmsPenRules = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:pgmsPenRulesUpdate', result);
            $scope.isSaving = false;
            $state.go("pgmsPenRules");
        };
        var onSaveError = function (result) {
             $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.pgmsPenRules.updateBy = $scope.loggedInUser.id;
            $scope.pgmsPenRules.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.pgmsPenRules.id != null) {
                PgmsPenRules.update($scope.pgmsPenRules, onSaveFinished,onSaveError);
                $rootScope.setWarningMessage('stepApp.pgmsPenRules.updated');

            } else {
                $scope.pgmsPenRules.createBy = $scope.loggedInUser.id;
                $scope.pgmsPenRules.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PgmsPenRules.save($scope.pgmsPenRules, onSaveFinished,onSaveError);
                $rootScope.setSuccessMessage('stepApp.pgmsPenRules.created');
            }
        };

        $scope.clear = function() {
            $state.dismiss('cancel');
        };
}]);
