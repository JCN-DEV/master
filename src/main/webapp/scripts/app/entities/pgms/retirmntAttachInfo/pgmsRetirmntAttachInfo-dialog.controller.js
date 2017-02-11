'use strict';

angular.module('stepApp').controller('PgmsRetirmntAttachInfoDialogController',
    ['$scope','$rootScope', '$stateParams', '$state', 'entity', 'PgmsRetirmntAttachInfo', 'User', 'Principal', 'DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, PgmsRetirmntAttachInfo, User, Principal, DateUtils) {

        $scope.pgmsRetirmntAttachInfo = entity;
        $scope.users = User.query({filter: 'pgmsPenGrSetup-is-null'});

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
            PgmsRetirmntAttachInfo.get({id : id}, function(result) {
                $scope.pgmsRetirmntAttachInfo = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:pgmsRetirmntAttachInfoUpdate', result);
            $scope.isSaving = false;
            $state.go("pgmsRetirmntAttachInfo");
        };
        var onSaveError = function (result) {
             $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.pgmsRetirmntAttachInfo.updateBy = $scope.loggedInUser.id;
            $scope.pgmsRetirmntAttachInfo.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.pgmsRetirmntAttachInfo.id != null) {
                PgmsRetirmntAttachInfo.update($scope.pgmsRetirmntAttachInfo, onSaveFinished, onSaveError);
                $rootScope.setWarningMessage('stepApp.pgmsRetirmntAttachInfo.updated');
            } else {
                $scope.pgmsRetirmntAttachInfo.createBy = $scope.loggedInUser.id;
                $scope.pgmsRetirmntAttachInfo.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PgmsRetirmntAttachInfo.save($scope.pgmsRetirmntAttachInfo, onSaveFinished, onSaveError);
                $rootScope.setSuccessMessage('stepApp.pgmsRetirmntAttachInfo.created');
            }
        };

        $scope.clear = function() {
            $state.dismiss('cancel');
        };
}]);
