'use strict';

angular.module('stepApp').controller('AlmAttendanceStatusDialogController',
    ['$scope', '$rootScope','$stateParams', '$state', 'entity', 'AlmAttendanceStatus', 'User', 'Principal', 'DateUtils',
        function($scope, $rootScope,$stateParams, $state, entity, AlmAttendanceStatus, User, Principal, DateUtils) {

            $scope.almAttendanceStatus = entity;

            $scope.load = function(id) {
                AlmAttendanceStatus.get({id : id}, function(result) {
                    $scope.almAttendanceStatus = result;
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

            var onSaveFinished = function (result) {
                $scope.$emit('stepApp:almAttendanceStatusUpdate', result);
                $scope.isSaving = false;
                $state.go("almAttendanceStatus");
            };

            $scope.save = function () {
                $scope.almAttendanceStatus.updateBy = $scope.loggedInUser.id;
                $scope.almAttendanceStatus.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.almAttendanceStatus.id != null) {
                    AlmAttendanceStatus.update($scope.almAttendanceStatus, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.almAttendanceStatus.updated');
                } else {
                    $scope.almAttendanceStatus.createBy = $scope.loggedInUser.id;
                    $scope.almAttendanceStatus.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmAttendanceStatus.save($scope.almAttendanceStatus, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.almAttendanceStatus.created');
                }
            };

        }]);
