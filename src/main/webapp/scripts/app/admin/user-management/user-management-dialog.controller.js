'use strict';

angular.module('stepApp').controller('UserManagementDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'User', 'Language',
        function ($scope, $stateParams, $modalInstance, entity, User, Language) {

            $scope.user = entity;
            $scope.authorities = ["ROLE_USER", "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYER", "ROLE_JPADMIN","ROLE_INSTITUTE"];

            Language.getAll().then(function (languages) {
                $scope.languages = languages;
            });

            var onSaveSuccess = function (result) {
                $scope.isSaving = false;
                $modalInstance.close(result);
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };

            $scope.save = function () {
                $scope.isSaving = true;
                if ($scope.user.id != null) {
                    User.update($scope.user, onSaveSuccess, onSaveError);
                } else {
                    User.save($scope.user, onSaveSuccess, onSaveError);
                }
            };

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
