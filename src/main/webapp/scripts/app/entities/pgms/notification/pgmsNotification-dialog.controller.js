'use strict';

angular.module('stepApp').controller('PgmsNotificationDialogController',
    ['$scope', '$stateParams', '$rootScope', '$state', 'entity', 'PgmsNotification', 'User', 'Principal', 'DateUtils',
        function($scope, $stateParams, $rootScope, $state, entity, PgmsNotification, User, Principal, DateUtils) {

        $scope.pgmsNotification = entity;
        $scope.users = User.query({filter: 'pgmsGrObtainSpecEmp-is-null'});
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
            PgmsNotification.get({id : id}, function(result) {
                $scope.pgmsNotification = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:pgmsNotificationUpdate', result);
            $scope.isSaving = false;
            $state.go("pgmsNotification");
        };

        $scope.save = function () {
            $scope.pgmsNotification.updateBy = $scope.loggedInUser.id;
            $scope.pgmsNotification.updateDate = DateUtils.convertLocaleDateToServer(new Date());
            if ($scope.pgmsNotification.id != null) {
                PgmsNotification.update($scope.pgmsNotification, onSaveFinished);
                $rootScope.setWarningMessage('stepApp.pgmsNotification.updated');
            } else {
                $scope.pgmsNotification.createBy = $scope.loggedInUser.id;
                $scope.pgmsNotification.createDate = DateUtils.convertLocaleDateToServer(new Date());
                PgmsNotification.save($scope.pgmsNotification, onSaveFinished);
                $rootScope.setSuccessMessage('stepApp.pgmsNotification.created');
            }
        };

        $scope.clear = function() {
            $state.dismiss('cancel');
        };

        $scope.calendar = {
            opened: {},
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };
}]);
