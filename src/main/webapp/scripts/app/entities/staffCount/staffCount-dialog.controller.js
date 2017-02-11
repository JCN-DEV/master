'use strict';

angular.module('stepApp').controller('StaffCountDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'StaffCount', 'Institute', 'User', 'Principal',
        function ($scope, $stateParams, $modalInstance, entity, StaffCount, Institute, User, Principal) {

            $scope.staffCount = entity;
            $scope.institutes = Institute.query({size: 500});
            $scope.users = User.query({size: 500});

            $scope.load = function (id) {
                StaffCount.get({id: id}, function (result) {
                    $scope.staffCount = result;
                });
            };

            Principal.identity().then(function (account) {
                $scope.account = account;
                $scope.staffCount.manager = User.get({login: $scope.account.login});
                $scope.staffCount.institute = Institute.get({id: 'my'});
            });


            var onSaveSuccess = function (result) {
                $scope.$emit('stepApp:staffCountUpdate', result);
                $modalInstance.close(result);
                $scope.isSaving = false;
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };

            $scope.save = function () {
                $scope.isSaving = true;
                if ($scope.staffCount.id != null) {
                    StaffCount.update($scope.staffCount, onSaveSuccess, onSaveError);
                } else {
                    StaffCount.save($scope.staffCount, onSaveSuccess, onSaveError);
                }
            };

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
