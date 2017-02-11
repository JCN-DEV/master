'use strict';

angular.module('stepApp').controller('AlmLeaveGroupDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'AlmLeaveGroup', 'User', 'Principal', 'DateUtils',
        function($scope, $stateParams, $state, entity, AlmLeaveGroup, User, Principal, DateUtils) {

            $scope.almLeaveGroup = entity;
            $scope.load = function(id) {
                AlmLeaveGroup.get({id : id}, function(result) {
                    $scope.almLeaveGroup = result;
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
                $scope.$emit('stepApp:almLeaveGroupUpdate', result);
                $scope.isSaving = false;
                $state.go("almLeaveGroup");
            };

            $scope.save = function () {
                $scope.almLeaveGroup.updateBy = $scope.loggedInUser.id;
                $scope.almLeaveGroup.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.almLeaveGroup.id != null) {
                    AlmLeaveGroup.update($scope.almLeaveGroup, onSaveFinished);
                } else {
                    $scope.almLeaveGroup.createBy = $scope.loggedInUser.id;
                    $scope.almLeaveGroup.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmLeaveGroup.save($scope.almLeaveGroup, onSaveFinished);
                }
            };
        }]);

