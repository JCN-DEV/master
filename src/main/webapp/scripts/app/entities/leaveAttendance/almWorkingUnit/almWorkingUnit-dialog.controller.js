'use strict';

angular.module('stepApp').controller('AlmWorkingUnitDialogController',
    ['$scope', '$rootScope','$stateParams', '$state', 'entity', 'AlmWorkingUnit', 'User', 'Principal', 'DateUtils', 'AlmShiftSetup',
        function($scope, $rootScope, $stateParams, $state, entity, AlmWorkingUnit, User, Principal, DateUtils, AlmShiftSetup) {

            $scope.almWorkingUnit = entity;
            $scope.almshiftsetups = [];
            AlmShiftSetup.query(function(result){
                angular.forEach(result,function(dtoInfo){
                    if(dtoInfo.activeStatus){
                        $scope.almshiftsetups.push(dtoInfo);
                    }
                });
            });
            $scope.load = function(id) {
                AlmWorkingUnit.get({id : id}, function(result) {
                    $scope.almWorkingUnit = result;

                    var sunStartTime = $scope.almWorkingUnit.sunStartTime.split(":");
                    var sunDelayTime = $scope.almWorkingUnit.sunDelayTime.split(":");
                    var sunEndTime   = $scope.almWorkingUnit.sunEndTime.split(":");
                    var monStartTime = $scope.almWorkingUnit.monStartTime.split(":");
                    var monDelayTime = $scope.almWorkingUnit.monDelayTime.split(":");
                    var monEndTime   = $scope.almWorkingUnit.monEndTime.split(":");
                    var tueStartTime = $scope.almWorkingUnit.tueStartTime.split(":");
                    var tueDelayTime = $scope.almWorkingUnit.tueDelayTime.split(":");
                    var tueEndTime   = $scope.almWorkingUnit.tueEndTime.split(":");
                    var wedStartTime = $scope.almWorkingUnit.wedStartTime.split(":");
                    var wedDelayTime = $scope.almWorkingUnit.wedDelayTime.split(":");
                    var wedEndTime   = $scope.almWorkingUnit.wedEndTime.split(":");
                    var thuStartTime = $scope.almWorkingUnit.thuStartTime.split(":");
                    var thuDelayTime = $scope.almWorkingUnit.thuDelayTime.split(":");
                    var thuEndTime   = $scope.almWorkingUnit.thuEndTime.split(":");
                    var friStartTime = $scope.almWorkingUnit.friStartTime.split(":");
                    var friDelayTime = $scope.almWorkingUnit.friDelayTime.split(":");
                    var friEndTime   = $scope.almWorkingUnit.friEndTime.split(":");
                    var satStartTime = $scope.almWorkingUnit.satStartTime.split(":");
                    var satDelayTime = $scope.almWorkingUnit.satDelayTime.split(":");
                    var satEndTime   = $scope.almWorkingUnit.satEndTime.split(":");

                   /* var officeStartValues   = $scope.almWorkingUnit.officeStart.split(":");
                    var delayTimeValues     = $scope.almWorkingUnit.officeStart.split(":");
                    var absentTimeValues    = $scope.almWorkingUnit.officeStart.split(":");
                    var officeEndValues     = $scope.almWorkingUnit.officeStart.split(":");
                    console.log('der' + JSON.stringify(officeStartValues));*/

                    $scope.almWorkingUnit.sunStartTimeHour = sunStartTime[0];
                    $scope.almWorkingUnit.sunDelayTimeHour = sunDelayTime[0];
                    $scope.almWorkingUnit.sunEndTimeHour = sunEndTime[0];
                    $scope.almWorkingUnit.monStartTimeHour = monStartTime[0];
                    $scope.almWorkingUnit.monDelayTimeHour = monDelayTime[0];
                    $scope.almWorkingUnit.monEndTimeHour = monEndTime[0];
                    $scope.almWorkingUnit.tueStartTimeHour = tueStartTime[0];
                    $scope.almWorkingUnit.tueDelayTimeHour = tueDelayTime[0];
                    $scope.almWorkingUnit.tueEndTimeHour = tueEndTime[0];
                    $scope.almWorkingUnit.wedStartTimeHour = wedStartTime[0];
                    $scope.almWorkingUnit.wedDelayTimeHour = wedDelayTime[0];
                    $scope.almWorkingUnit.wedEndTimeHour = wedEndTime[0];
                    $scope.almWorkingUnit.thuStartTimeHour = thuStartTime[0];
                    $scope.almWorkingUnit.thuDelayTimeHour = thuDelayTime[0];
                    $scope.almWorkingUnit.thuEndTimeHour = thuEndTime[0];
                    $scope.almWorkingUnit.friStartTimeHour = friStartTime[0];
                    $scope.almWorkingUnit.friDelayTimeHour = friDelayTime[0];
                    $scope.almWorkingUnit.friEndTimeHour = friEndTime[0];
                    $scope.almWorkingUnit.satStartTimeHour = satStartTime[0];
                    $scope.almWorkingUnit.satDelayTimeHour = satDelayTime[0];
                    $scope.almWorkingUnit.satEndTimeHour = satEndTime[0];

                    $scope.almWorkingUnit.sunStartTimeMin = sunStartTime[1];
                    $scope.almWorkingUnit.sunDelayTimeMin = sunDelayTime[1];
                    $scope.almWorkingUnit.sunEndTimeMin = sunEndTime[1];
                    $scope.almWorkingUnit.monStartTimeMin = monStartTime[1];
                    $scope.almWorkingUnit.monDelayTimeMin = monDelayTime[1];
                    $scope.almWorkingUnit.monEndTimeMin = monEndTime[1];
                    $scope.almWorkingUnit.tueStartTimeMin = tueStartTime[1];
                    $scope.almWorkingUnit.tueDelayTimeMin = tueDelayTime[1];
                    $scope.almWorkingUnit.tueEndTimeMin = tueEndTime[1];
                    $scope.almWorkingUnit.wedStartTimeMin = wedStartTime[1];
                    $scope.almWorkingUnit.wedDelayTimeMin = wedDelayTime[1];
                    $scope.almWorkingUnit.wedEndTimeMin = wedEndTime[1];
                    $scope.almWorkingUnit.thuStartTimeMin = thuStartTime[1];
                    $scope.almWorkingUnit.thuDelayTimeMin = thuDelayTime[1];
                    $scope.almWorkingUnit.thuEndTimeMin = thuEndTime[1];
                    $scope.almWorkingUnit.friStartTimeMin = friStartTime[1];
                    $scope.almWorkingUnit.friDelayTimeMin = friDelayTime[1];
                    $scope.almWorkingUnit.friEndTimeMin = friEndTime[1];
                    $scope.almWorkingUnit.satStartTimeMin = satStartTime[1];
                    $scope.almWorkingUnit.satDelayTimeMin = satDelayTime[1];
                    $scope.almWorkingUnit.satEndTimeMin = satEndTime[1];

                   /* $scope.almWorkingUnit.officeStartHour = officeStartValues[0];
                    $scope.almWorkingUnit.delayTimeHour = delayTimeValues[0];
                    $scope.almWorkingUnit.absentTimeHour = absentTimeValues[0];
                    $scope.almWorkingUnit.officeEndHour = officeEndValues[0];

                    $scope.almWorkingUnit.officeStartMin = officeStartValues[1];
                    $scope.almWorkingUnit.delayTimeMin = delayTimeValues[1];
                    $scope.almWorkingUnit.absentTimeMin = absentTimeValues[1];
                    $scope.almWorkingUnit.officeEndMin = officeEndValues[1];*/
                });
            };
            if($stateParams.id){
                $scope.load($stateParams.id);
            }

            $scope.isExitsData = true;
            $scope.duplicateCheckByDayName = function(){
                $scope.isExitsData = true;
                AlmWorkingUnit.query(function(result){
                    angular.forEach(result,function(dtoInfo){
                        if(dtoInfo.dayName == $scope.almWorkingUnit.dayName && dtoInfo.almShiftSetup.id == $scope.almWorkingUnit.almShiftSetup.id){
                            $scope.isExitsData = false;
                        }
                    });
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
                $scope.$emit('stepApp:almWorkingUnitUpdate', result);
                $scope.isSaving = false;
                $state.go("almWorkingUnit");
            };

            $scope.save = function () {
                $scope.almWorkingUnit.sunStartTime  = $scope.almWorkingUnit.sunStartTimeHour    + ':' +    $scope.almWorkingUnit.sunStartTimeMin;
                $scope.almWorkingUnit.sunDelayTime  = $scope.almWorkingUnit.sunDelayTimeHour    + ':' +    $scope.almWorkingUnit.sunDelayTimeMin;
                $scope.almWorkingUnit.sunEndTime    = $scope.almWorkingUnit.sunEndTimeHour      + ':' +    $scope.almWorkingUnit.sunEndTimeMin;
                $scope.almWorkingUnit.monStartTime  = $scope.almWorkingUnit.monStartTimeHour    + ':' +    $scope.almWorkingUnit.monStartTimeMin;
                $scope.almWorkingUnit.monDelayTime  = $scope.almWorkingUnit.monDelayTimeHour    + ':' +    $scope.almWorkingUnit.monDelayTimeMin;
                $scope.almWorkingUnit.monEndTime    = $scope.almWorkingUnit.monEndTimeHour      + ':' +    $scope.almWorkingUnit.monEndTimeMin;
                $scope.almWorkingUnit.tueStartTime  = $scope.almWorkingUnit.tueStartTimeHour    + ':' +    $scope.almWorkingUnit.tueStartTimeMin;
                $scope.almWorkingUnit.tueDelayTime  = $scope.almWorkingUnit.tueDelayTimeHour    + ':' +    $scope.almWorkingUnit.tueDelayTimeMin;
                $scope.almWorkingUnit.tueEndTime    = $scope.almWorkingUnit.tueEndTimeHour      + ':' +    $scope.almWorkingUnit.tueEndTimeMin;
                $scope.almWorkingUnit.wedStartTime  = $scope.almWorkingUnit.wedStartTimeHour    + ':' +    $scope.almWorkingUnit.wedStartTimeMin;
                $scope.almWorkingUnit.wedDelayTime  = $scope.almWorkingUnit.wedDelayTimeHour    + ':' +    $scope.almWorkingUnit.wedDelayTimeMin;
                $scope.almWorkingUnit.wedEndTime    = $scope.almWorkingUnit.wedEndTimeHour      + ':' +    $scope.almWorkingUnit.wedEndTimeMin;
                $scope.almWorkingUnit.thuStartTime  = $scope.almWorkingUnit.thuStartTimeHour    + ':' +    $scope.almWorkingUnit.thuStartTimeMin;
                $scope.almWorkingUnit.thuDelayTime  = $scope.almWorkingUnit.thuDelayTimeHour    + ':' +    $scope.almWorkingUnit.thuDelayTimeMin;
                $scope.almWorkingUnit.thuEndTime    = $scope.almWorkingUnit.thuEndTimeHour      + ':' +    $scope.almWorkingUnit.thuEndTimeMin;
                $scope.almWorkingUnit.friStartTime  = $scope.almWorkingUnit.friStartTimeHour    + ':' +    $scope.almWorkingUnit.friStartTimeMin;
                $scope.almWorkingUnit.friDelayTime  = $scope.almWorkingUnit.friDelayTimeHour    + ':' +    $scope.almWorkingUnit.friDelayTimeMin;
                $scope.almWorkingUnit.friEndTime    = $scope.almWorkingUnit.friEndTimeHour      + ':' +    $scope.almWorkingUnit.friEndTimeMin;
                $scope.almWorkingUnit.satStartTime  = $scope.almWorkingUnit.satStartTimeHour    + ':' +    $scope.almWorkingUnit.satStartTimeMin;
                $scope.almWorkingUnit.satDelayTime  = $scope.almWorkingUnit.satDelayTimeHour    + ':' +    $scope.almWorkingUnit.satDelayTimeMin;
                $scope.almWorkingUnit.satEndTime    = $scope.almWorkingUnit.satEndTimeHour      + ':' +    $scope.almWorkingUnit.satEndTimeMin;


                //$scope.almWorkingUnit.officeStart = $scope.almWorkingUnit.officeStartHour + ':' +     $scope.almWorkingUnit.officeStartMin;
                //$scope.almWorkingUnit.delayTime   = $scope.almWorkingUnit.delayTimeHour   + ':' +     $scope.almWorkingUnit.delayTimeMin ;
                //$scope.almWorkingUnit.absentTime  = $scope.almWorkingUnit.absentTimeHour  + ':' +     $scope.almWorkingUnit.absentTimeMin ;
                //$scope.almWorkingUnit.officeEnd   = $scope.almWorkingUnit.officeEndHour   + ':' +     $scope.almWorkingUnit.officeEndMin  ;
                $scope.almWorkingUnit.updateBy      = $scope.loggedInUser.id;

                $scope.almWorkingUnit.updateDate = DateUtils.convertLocaleDateToServer(new Date());
                if ($scope.almWorkingUnit.id != null) {
                    AlmWorkingUnit.update($scope.almWorkingUnit, onSaveFinished);
                    $rootScope.setWarningMessage('stepApp.almWorkingUnit.updated');
                } else {
                    $scope.almWorkingUnit.createBy = $scope.loggedInUser.id;
                    $scope.almWorkingUnit.createDate = DateUtils.convertLocaleDateToServer(new Date());
                    AlmWorkingUnit.save($scope.almWorkingUnit, onSaveFinished);
                    $rootScope.setSuccessMessage('stepApp.almWorkingUnit.created');
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

            $scope.onCheckedSunday      = function () {
                if(!$scope.almWorkingUnit.sunday){
                    $scope.almWorkingUnit.sunStartTimeHour  = null;
                    $scope.almWorkingUnit.sunDelayTimeHour  = null;
                    $scope.almWorkingUnit.sunEndTimeHour    = null;
                    $scope.almWorkingUnit.sunStartTimeMin = null;
                    $scope.almWorkingUnit.sunDelayTimeMin = null;
                    $scope.almWorkingUnit.sunEndTimeMin = null;
                }

            };
            $scope.onCheckedMonday      = function () {
                if(!$scope.almWorkingUnit.monday){
                    $scope.almWorkingUnit.monStartTimeHour = null;
                    $scope.almWorkingUnit.monDelayTimeHour = null;
                    $scope.almWorkingUnit.monEndTimeHour = null;
                    $scope.almWorkingUnit.monStartTimeMin = null;
                    $scope.almWorkingUnit.monDelayTimeMin = null;
                    $scope.almWorkingUnit.monEndTimeMin = null;
                }

            };
            $scope.onCheckedTuesday      = function () {
                if(!$scope.almWorkingUnit.tuesday){
                    $scope.almWorkingUnit.tueEndTimeHour  = null;
                    $scope.almWorkingUnit.tueStartTimeMin = null;
                    $scope.almWorkingUnit.tueDelayTimeMin = null;
                    $scope.almWorkingUnit.tueEndTimeMin = null;
                    $scope.almWorkingUnit.tueStartTimeHour= null;
                    $scope.almWorkingUnit.tueDelayTimeHour= null;
                }

            };
            $scope.onCheckedWednesday      = function () {
                if(!$scope.almWorkingUnit.wednesday){
                    $scope.almWorkingUnit.wedStartTimeHour = null;
                    $scope.almWorkingUnit.wedDelayTimeHour = null;
                    $scope.almWorkingUnit.wedEndTimeHour   = null;
                    $scope.almWorkingUnit.wedStartTimeMin = null;
                    $scope.almWorkingUnit.wedDelayTimeMin = null;
                    $scope.almWorkingUnit.wedEndTimeMin = null;
                }

            };
            $scope.onCheckedThurseday      = function () {
                if(!$scope.almWorkingUnit.thursday){
                    $scope.almWorkingUnit.thuStartTimeHour = null;
                    $scope.almWorkingUnit.thuDelayTimeHour = null;
                    $scope.almWorkingUnit.thuEndTimeHour   = null;
                    $scope.almWorkingUnit.thuStartTimeMin = null;
                    $scope.almWorkingUnit.thuDelayTimeMin = null;
                    $scope.almWorkingUnit.thuEndTimeMin = null;
                }

            };
            $scope.onCheckedFriday      = function () {
                if(!$scope.almWorkingUnit.friday){
                    $scope.almWorkingUnit.friStartTimeHour  = null;
                    $scope.almWorkingUnit.friDelayTimeHour  = null;
                    $scope.almWorkingUnit.friEndTimeHour    = null;
                    $scope.almWorkingUnit.friStartTimeMin = null;
                    $scope.almWorkingUnit.friDelayTimeMin = null;
                    $scope.almWorkingUnit.friEndTimeMin = null;
                }

            };
            $scope.onCheckedSaturday      = function () {
                if(!$scope.almWorkingUnit.saturday){
                    $scope.almWorkingUnit.satStartTimeHour = null;
                    $scope.almWorkingUnit.satDelayTimeHour = null;
                    $scope.almWorkingUnit.satEndTimeHour   = null;
                    $scope.almWorkingUnit.satStartTimeMin = null;
                    $scope.almWorkingUnit.satDelayTimeMin = null;
                    $scope.almWorkingUnit.satEndTimeMin = null;
                }
            };


            $scope.clear = function(){
                $scope.almWorkingUnit.sunStartTimeHour  = null;
                $scope.almWorkingUnit.sunDelayTimeHour  = null;
                $scope.almWorkingUnit.sunEndTimeHour    = null;
                $scope.almWorkingUnit.sunStartTimeMin = null;
                $scope.almWorkingUnit.sunDelayTimeMin = null;
                $scope.almWorkingUnit.sunEndTimeMin = null;
                $scope.almWorkingUnit.monStartTimeHour = null;
                $scope.almWorkingUnit.monDelayTimeHour = null;
                $scope.almWorkingUnit.monEndTimeHour = null;
                $scope.almWorkingUnit.monStartTimeMin = null;
                $scope.almWorkingUnit.monDelayTimeMin = null;
                $scope.almWorkingUnit.monEndTimeMin = null;
                $scope.almWorkingUnit.tueStartTimeHour= null;
                $scope.almWorkingUnit.tueDelayTimeHour= null;
                $scope.almWorkingUnit.tueEndTimeHour  = null;
                $scope.almWorkingUnit.tueStartTimeMin = null;
                $scope.almWorkingUnit.tueDelayTimeMin = null;
                $scope.almWorkingUnit.tueEndTimeMin = null;
                $scope.almWorkingUnit.wedStartTimeHour = null;
                $scope.almWorkingUnit.wedDelayTimeHour = null;
                $scope.almWorkingUnit.wedEndTimeHour   = null;
                $scope.almWorkingUnit.wedStartTimeMin = null;
                $scope.almWorkingUnit.wedDelayTimeMin = null;
                $scope.almWorkingUnit.wedEndTimeMin = null;
                $scope.almWorkingUnit.thuStartTimeHour = null;
                $scope.almWorkingUnit.thuDelayTimeHour = null;
                $scope.almWorkingUnit.thuEndTimeHour   = null;
                $scope.almWorkingUnit.thuStartTimeMin = null;
                $scope.almWorkingUnit.thuDelayTimeMin = null;
                $scope.almWorkingUnit.thuEndTimeMin = null;
                $scope.almWorkingUnit.friStartTimeHour  = null;
                $scope.almWorkingUnit.friDelayTimeHour  = null;
                $scope.almWorkingUnit.friEndTimeHour    = null;
                $scope.almWorkingUnit.friStartTimeMin = null;
                $scope.almWorkingUnit.friDelayTimeMin = null;
                $scope.almWorkingUnit.friEndTimeMin = null;
                $scope.almWorkingUnit.satStartTimeHour = null;
                $scope.almWorkingUnit.satDelayTimeHour = null;
                $scope.almWorkingUnit.satEndTimeHour   = null;
                $scope.almWorkingUnit.satStartTimeMin = null;
                $scope.almWorkingUnit.satDelayTimeMin = null;
                $scope.almWorkingUnit.satEndTimeMin = null;
            };

            $scope.clear();

            $scope.onCheckedIsHalfDayYes = function () {
                $scope.almWorkingUnit.halfDay = 'First';
            };
            $scope.onCheckedIsHalfDayNo = function () {
                $scope.almWorkingUnit.halfDay = null;
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
