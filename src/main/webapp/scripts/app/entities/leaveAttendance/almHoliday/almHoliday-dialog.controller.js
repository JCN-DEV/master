'use strict';

angular.module('stepApp').controller('AlmHolidayDialogController',
    ['$scope', '$rootScope','$stateParams', 'entity', 'Principal', 'AlmHoliday', '$state', 'User', 'DateUtils',
        function($scope, $rootScope,$stateParams,  entity, Principal, AlmHoliday, $state, User, DateUtils) {

        $scope.almHoliday = entity;
        $scope.load = function(id) {
            AlmHoliday.get({id : id}, function(result) {
                $scope.almHoliday = result;
            });
        };


            var onSaveSuccess = function (result) {
                $scope.$emit('stepApp:almHolidayUpdate', result);
                $scope.isSaving = false;
                $state.go('almHoliday');
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };

            $scope.save = function ()
            {
                Principal.identity().then(function (account)
                {
                    User.get({login: account.login}, function (result)
                    {
                        $scope.isSaving = true;
                        $scope.almHoliday.updateBy = result.id;
                        $scope.almHoliday.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                        if ($scope.almHoliday.id != null)
                        {
                            AlmHoliday.update($scope.almHoliday, onSaveSuccess, onSaveError);
                            $rootScope.setWarningMessage('stepApp.almHoliday.updated');
                        }
                        else
                        {
                            $scope.almHoliday.createBy = result.id;
                            $scope.almHoliday.createDate = DateUtils.convertLocaleDateToServer(new Date());
                            AlmHoliday.save($scope.almHoliday, onSaveSuccess, onSaveError);
                            $rootScope.setSuccessMessage('stepApp.almHoliday.created');
                        }
                    });
                });
            };


        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };

        $scope.calendar = {
            opened: {},
            dateFormat: 'dd-MM-yyyy',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };
}]);
