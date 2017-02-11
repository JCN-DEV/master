'use strict';

angular.module('stepApp').controller('AlmWeekendConfigurationDialogController',
    ['$scope', '$rootScope','$stateParams', 'entity', 'AlmWeekendConfiguration', 'Principal', '$state', 'User', 'DateUtils',
        function($scope, $rootScope,$stateParams, entity, AlmWeekendConfiguration, Principal,  $state, User, DateUtils) {

            $scope.almWeekendConfiguration = entity;
            $scope.load = function(id) {
                AlmWeekendConfiguration.get({id : id}, function(result) {
                    $scope.almWeekendConfiguration = result;
                });
            };
            var onSaveSuccess = function (result) {
                $scope.$emit('stepApp:almWeekendConfigurationUpdate', result);
                $scope.isSaving = false;
                $state.go('almWeekendConfiguration');
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };

            $scope.isExitsData = true;
            $scope.duplicateCheckByDayName = function(){
                $scope.isExitsData = true;
                AlmWeekendConfiguration.query(function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.dayName == $scope.almWeekendConfiguration.dayName){
                            $scope.isExitsData = false;
                        }
                    });
                });
            };

            $scope.save = function ()
            {
                Principal.identity().then(function (account)
                {
                    User.get({login: account.login}, function (result)
                    {
                        $scope.isSaving = true;
                        $scope.almWeekendConfiguration.updateBy = result.id;
                        $scope.almWeekendConfiguration.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                        if ($scope.almWeekendConfiguration.id != null)
                        {
                            AlmWeekendConfiguration.update($scope.almWeekendConfiguration, onSaveSuccess, onSaveError);
                            $rootScope.setWarningMessage('stepApp.almWeekendConfiguration.updated');
                        }
                        else
                        {
                            $scope.almWeekendConfiguration.activeStatus = true;
                            $scope.almWeekendConfiguration.createBy = result.id;
                            $scope.almWeekendConfiguration.createDate = DateUtils.convertLocaleDateToServer(new Date());
                            AlmWeekendConfiguration.save($scope.almWeekendConfiguration, onSaveSuccess, onSaveError);
                            $rootScope.setSuccessMessage('stepApp.almWeekendConfiguration.created');
                        }
                    });
                });
            };
        }]);
