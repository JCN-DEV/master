'use strict';

angular.module('stepApp').controller('AlmEarningFrequencyDialogController',
    ['$scope', '$rootScope','$stateParams', '$state', 'entity', 'AlmEarningFrequency', 'User', 'Principal', 'DateUtils',
        function($scope, $rootScope, $stateParams, $state, entity, AlmEarningFrequency, User, Principal, DateUtils) {

            $scope.almEarningFrequency = entity;

            $scope.load = function(id) {
                AlmEarningFrequency.get({id : id}, function(result) {
                    $scope.almEarningFrequency = result;
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
                $scope.$emit('stepApp:almEarningFrequencyUpdate', result);
                $scope.isSaving = false;
                $state.go("almEarningFrequency");
            };

            $scope.save = function () {
                $scope.almEarningFrequency.updateBy = $scope.loggedInUser.id;
                $scope.almEarningFrequency.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.almEarningFrequency.id != null) {
                    AlmEarningFrequency.update($scope.almEarningFrequency, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.almEarningFrequency.updated');
                } else {
                    $scope.almEarningFrequency.createBy = $scope.loggedInUser.id;
                    $scope.almEarningFrequency.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmEarningFrequency.save($scope.almEarningFrequency, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.almEarningFrequency.created');
                }
            };

        }]);


