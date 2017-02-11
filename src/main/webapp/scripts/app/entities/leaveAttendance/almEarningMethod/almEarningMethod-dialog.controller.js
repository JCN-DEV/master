'use strict';

angular.module('stepApp').controller('AlmEarningMethodDialogController',
    ['$scope', '$rootScope','$stateParams', '$state', 'entity', 'AlmEarningMethod','User', 'Principal', 'DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, AlmEarningMethod, User, Principal, DateUtils) {

            $scope.almEarningMethod = entity;

            $scope.load = function(id) {
                AlmEarningMethod.get({id : id}, function(result) {
                    $scope.almEarningMethod = result;
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
                $scope.$emit('stepApp:almEarningMethodUpdate', result);
                $scope.isSaving = false;
                $state.go("almEarningMethod");
            };

            $scope.save = function () {
                $scope.almEarningMethod.updateBy = $scope.loggedInUser.id;
                $scope.almEarningMethod.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.almEarningMethod.id != null) {
                    AlmEarningMethod.update($scope.almEarningMethod, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.almEarningMethod.updated');
                } else {
                    $scope.almEarningMethod.createBy = $scope.loggedInUser.id;
                    $scope.almEarningMethod.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmEarningMethod.save($scope.almEarningMethod, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.almEarningMethod.created');
                }
            };

        }]);


