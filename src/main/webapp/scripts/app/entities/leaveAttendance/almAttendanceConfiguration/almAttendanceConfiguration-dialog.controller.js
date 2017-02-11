'use strict';

angular.module('stepApp').controller('AlmAttendanceConfigurationDialogController',
    ['$scope', '$rootScope','$stateParams', '$state', 'entity', 'AlmAttendanceConfiguration','User','Principal','DateUtils', 'AlmShiftSetup','HrEmployeeInfo',
        function($scope, $rootScope, $stateParams, $state, entity, AlmAttendanceConfiguration, User, Principal, DateUtils, AlmShiftSetup, HrEmployeeInfo) {

            $scope.almAttendanceConfiguration = entity;
            $scope.hremployeeinfos = [];
            $scope.almshiftsetups = [];

            HrEmployeeInfo.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.hremployeeinfos.push(dtoInfo);
                    }
                });
            });

            AlmShiftSetup.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.almshiftsetups.push(dtoInfo);
                    }
                });
            });
            $scope.load = function(id) {
                AlmAttendanceConfiguration.get({id : id}, function(result) {
                    $scope.almAttendanceConfiguration = result;
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
                $scope.$emit('stepApp:almAttendanceConfigurationUpdate', result);
                $scope.isSaving = false;
                $state.go("almAttendanceConfiguration");
            };

            $scope.save = function () {
                $scope.almAttendanceConfiguration.updateBy = $scope.loggedInUser.id;
                $scope.almAttendanceConfiguration.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.almAttendanceConfiguration.id != null) {
                    AlmAttendanceConfiguration.update($scope.almAttendanceConfiguration, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.almAttendanceConfiguration.updated');
                } else {
                    $scope.almAttendanceConfiguration.employeeType = 'will be added later.';
                    $scope.almAttendanceConfiguration.createBy = $scope.loggedInUser.id;
                    $scope.almAttendanceConfiguration.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmAttendanceConfiguration.save($scope.almAttendanceConfiguration, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.almAttendanceConfiguration.created');
                }
            };

            $scope.calendar = {
                opened: {},
                dateFormat: 'yyyy-MM-dd',
                dateOptions: {},
                open: function ($event, which) {
                    $event.preventDefault();
                    $event.stopPropagation();
                    $scope.calendar.opened[which] = true;
                }
            };

        }]);

