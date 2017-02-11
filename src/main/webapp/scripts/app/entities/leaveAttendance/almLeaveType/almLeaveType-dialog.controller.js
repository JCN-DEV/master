'use strict';

angular.module('stepApp').controller('AlmLeaveTypeDialogController',
    ['$scope', '$rootScope','$stateParams', '$state', 'entity', 'AlmLeaveType', 'User', 'Principal', 'DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, AlmLeaveType, User, Principal, DateUtils) {

            $scope.almLeaveType = entity;

            $scope.load = function(id) {
                AlmLeaveType.get({id : id}, function(result) {
                    $scope.almLeaveType = result;
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
                $scope.$emit('stepApp:almLeaveTypeUpdate', result);
                $scope.isSaving = false;
                $state.go("almLeaveType");
            };

            $scope.save = function () {
                $scope.almLeaveType.updateBy = $scope.loggedInUser.id;
                $scope.almLeaveType.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.almLeaveType.id != null) {
                    AlmLeaveType.update($scope.almLeaveType, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.almLeaveType.updated');
                } else {
                    $scope.almLeaveType.createBy = $scope.loggedInUser.id;
                    $scope.almLeaveType.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmLeaveType.save($scope.almLeaveType, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.almLeaveType.created');
                }
            };

        }]);

