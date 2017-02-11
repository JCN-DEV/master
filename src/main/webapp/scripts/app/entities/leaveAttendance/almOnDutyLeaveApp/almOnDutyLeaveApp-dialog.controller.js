'use strict';

angular.module('stepApp').controller('AlmOnDutyLeaveAppDialogController',
    ['$scope','$rootScope', '$stateParams', '$state', 'entity', 'AlmOnDutyLeaveApp', 'User', 'Principal', 'DateUtils', 'HrEmployeeInfo', 'AlmDutySide',
        function($scope, $rootScope, $stateParams, $state, entity, AlmOnDutyLeaveApp, User, Principal, DateUtils, HrEmployeeInfo, AlmDutySide) {

            $scope.almOnDutyLeaveApp = entity;
            $scope.hremployeeinfos = [];
            $scope.almdutysides = [];

            HrEmployeeInfo.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.hremployeeinfos.push(dtoInfo);
                    }
                });
            });

            AlmDutySide.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.almdutysides.push(dtoInfo);
                    }
                });
            });

            $scope.load = function(id) {
                AlmOnDutyLeaveApp.get({id : id}, function(result) {
                    $scope.almOnDutyLeaveApp = result;
                });
            };

            HrEmployeeInfo.get({id: 'my'}, function (result) {

                $scope.hrEmployeeInfo = result;
                $scope.designationName      = $scope.hrEmployeeInfo.designationInfo.designationName;
                $scope.departmentName       = $scope.hrEmployeeInfo.departmentInfo.departmentName;
                $scope.workAreaName         = $scope.hrEmployeeInfo.workArea.typeName;
            });

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
                $scope.$emit('stepApp:almOnDutyLeaveAppUpdate', result);
                $scope.isSaving = false;
                $state.go("almOnDutyLeaveApp");
            };

            $scope.clear = function(){
                $scope.applicationDate=  null,
                    $scope.dutyDate = null,
                    $scope.dutyInTimeHour = null,
                    $scope.dutyInTimeMin = null,
                    $scope.dutyOutTimeHour = null,
                    $scope.dutyOutTimeMin= null,
                    $scope.endDutyDate= null,
                    $scope.reason= null,
                    $scope.activeStatus= true,
                    $scope.isMoreDay= false
            };

            $scope.save = function () {
                $scope.almOnDutyLeaveApp.applicationLeaveStatus = 'Pending';
                if(!$scope.almOnDutyLeaveApp.isMoreDay){
                    $scope.almOnDutyLeaveApp.endDutyDate = DateUtils.convertLocaleDateToServer(new Date());
                }
                $scope.almOnDutyLeaveApp.updateBy = $scope.loggedInUser.id;
                $scope.almOnDutyLeaveApp.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.almOnDutyLeaveApp.id != null) {
                    AlmOnDutyLeaveApp.update($scope.almOnDutyLeaveApp, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.almOnDutyLeaveApp.updated');
                } else {
                    $scope.almOnDutyLeaveApp.employeeInfo = $scope.hrEmployeeInfo;
                    $scope.almOnDutyLeaveApp.createBy = $scope.loggedInUser.id;
                    $scope.almOnDutyLeaveApp.applicationDate = DateUtils.convertLocaleDateToServer(new Date());
                    $scope.almOnDutyLeaveApp.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmOnDutyLeaveApp.save($scope.almOnDutyLeaveApp, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.almOnDutyLeaveApp.created');
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

            $scope.hourData = {
                hourOptions: [
                    '00',
                    '01',
                    '02',
                    '03',
                    '04',
                    '05',
                    '06',
                    '07',
                    '08',
                    '09',
                    '10',
                    '11',
                    '12',
                    '13',
                    '14',
                    '15',
                    '16',
                    '17',
                    '18',
                    '19',
                    '20',
                    '21',
                    '22',
                    '23'
                ]
            };

            $scope.minData = {
                minOptions: [
                    '00',
                    '01',
                    '02',
                    '03',
                    '04',
                    '05',
                    '06',
                    '07',
                    '08',
                    '09',
                    '10',
                    '11',
                    '12',
                    '13',
                    '14',
                    '15',
                    '16',
                    '17',
                    '18',
                    '19',
                    '20',
                    '21',
                    '22',
                    '23',
                    '24',
                    '25',
                    '26',
                    '27',
                    '28',
                    '29',
                    '30',
                    '31',
                    '32',
                    '33',
                    '34',
                    '35',
                    '36',
                    '37',
                    '38',
                    '39',
                    '40',
                    '41',
                    '42',
                    '43',
                    '44',
                    '45',
                    '46',
                    '47',
                    '48',
                    '49',
                    '50',
                    '51',
                    '52',
                    '53',
                    '54',
                    '55',
                    '56',
                    '57',
                    '58',
                    '59'
                ]
            };

        }]);
