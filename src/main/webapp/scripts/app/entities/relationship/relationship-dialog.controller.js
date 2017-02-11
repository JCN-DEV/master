'use strict';

angular.module('stepApp').controller('RelationshipDialogController',
    ['$scope', '$rootScope','$stateParams', '$state', 'entity', 'Relationship', 'User', 'Principal', 'DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, Relationship, User, Principal, DateUtils) {

            $scope.relationship = entity;

            $scope.load = function(id) {
                Relationship.get({id : id}, function(result) {
                    $scope.relationship = result;
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
                $scope.$emit('stepApp:relationshipUpdate', result);
                $scope.isSaving = false;
                $state.go("relationship");
            };

            $scope.save = function () {
                $scope.relationship.updateBy = $scope.loggedInUser.id;
                $scope.relationship.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.relationship.id != null) {
                    Relationship.update($scope.relationship, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.relationship.updated');
                } else {
                    $scope.relationship.createBy = $scope.loggedInUser.id;
                    $scope.relationship.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    Relationship.save($scope.relationship, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.relationship.created');

                }
            };

        }]);





