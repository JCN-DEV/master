'use strict';

angular.module('stepApp')
    .controller('NameCnclApplicationCheckListController',
    ['$scope', '$rootScope','NameCnclApplicationLogEmployeeCode', '$state', '$stateParams', 'Sessions', 'Training', 'InstEmployeeCode','MpoApplicationLogEmployeeCode','MpoApplication', 'PayScale', 'Jobapplication', 'Principal', 'ParseLinks', 'ApplicantEducation', 'Institute', 'User', 'Upazila', 'Course', 'Employee', 'DataUtils', 'StaffCount',
    function ($scope, $rootScope,NameCnclApplicationLogEmployeeCode, $state, $stateParams, Sessions, Training, InstEmployeeCode,MpoApplicationLogEmployeeCode,MpoApplication, PayScale, Jobapplication, Principal, ParseLinks, ApplicantEducation, Institute, User, Upazila, Course, Employee, DataUtils, StaffCount) {

        $scope.showSearchForm = true;
        NameCnclApplicationLogEmployeeCode.get({'code':$stateParams.code}, function(result) {
            console.log(result);
            $scope.timescaleApplicationlogs = result;

        });


    }]);
