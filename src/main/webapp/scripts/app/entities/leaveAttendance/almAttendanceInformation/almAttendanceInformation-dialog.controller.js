'use strict';

angular.module('stepApp').controller('AlmAttendanceInformationDialogController',
    ['$scope', '$stateParams', '$state', 'entity', 'AlmAttendanceInformation','User','Principal','DateUtils', 'HrEmployeeInfo', 'AlmAttendanceConfiguration', 'AlmShiftSetup', 'AlmAttendanceStatus',
        function($scope, $stateParams, $state, entity, AlmAttendanceInformation, User, Principal, DateUtils, HrEmployeeInfo, AlmAttendanceConfiguration, AlmShiftSetup, AlmAttendanceStatus) {

            $scope.almAttendanceInformation = entity;
            $scope.hremployeeinfos = [];
            $scope.almattendanceconfigurations = [];
            $scope.almshiftsetups = [];
            $scope.almattendancestatuss = [];

            HrEmployeeInfo.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.hremployeeinfos.push(dtoInfo);
                    }
                });
            });

            AlmAttendanceConfiguration.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.almattendanceconfigurations.push(dtoInfo);
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

            AlmAttendanceStatus.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.almattendancestatuss.push(dtoInfo);
                    }
                });
            });

            $scope.load = function(id) {
                AlmAttendanceInformation.get({id : id}, function(result) {
                    $scope.almAttendanceInformation = result;
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
                $scope.$emit('stepApp:almAttendanceInformationUpdate', result);
                $scope.isSaving = false;
                $state.go("almAttendanceInformation");
            };

            $scope.save = function () {
                $scope.almAttendanceInformation.updateBy = $scope.loggedInUser.id;
                $scope.almAttendanceInformation.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.almAttendanceInformation.id != null) {
                    AlmAttendanceInformation.update($scope.almAttendanceInformation, onSaveFinished);
                } else {
                    $scope.almAttendanceInformation.createBy = $scope.loggedInUser.id;
                    $scope.almAttendanceInformation.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmAttendanceInformation.save($scope.almAttendanceInformation, onSaveFinished);
                }
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

