'use strict';

angular.module('stepApp').controller('AlmDutySideDialogController',
    ['$scope', '$rootScope','$stateParams', '$state', 'entity', 'AlmDutySide', 'User', 'Principal', 'DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, AlmDutySide, User, Principal, DateUtils) {

            $scope.almDutySide = entity;

            $scope.load = function(id) {
                AlmDutySide.get({id : id}, function(result) {
                    $scope.almDutySide = result;
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
                $scope.$emit('stepApp:almDutySideUpdate', result);
                $scope.isSaving = false;
                $state.go("almDutySide");
            };

            $scope.save = function () {
                $scope.almDutySide.updateBy = $scope.loggedInUser.id;
                $scope.almDutySide.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.almDutySide.id != null) {
                    AlmDutySide.update($scope.almDutySide, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.almDutySide.updated');
                } else {
                    $scope.almDutySide.createBy = $scope.loggedInUser.id;
                    $scope.almDutySide.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmDutySide.save($scope.almDutySide, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.almDutySide.created');
                }
            };

        }]);


