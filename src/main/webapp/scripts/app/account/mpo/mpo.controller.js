'use strict';

angular.module('stepApp')
    .controller('MpoController',
     ['$scope', '$state', 'Sessions', 'Training', 'PayScale', 'Jobapplication', 'Principal', 'ParseLinks', 'ApplicantEducation', 'Institute', 'User', 'Upazila', 'Course', 'Employee', 'DataUtils', 'StaffCount','InstituteByLogin','CurrentInstEmployee',
     function ($scope, $state, Sessions, Training, PayScale, Jobapplication, Principal, ParseLinks, ApplicantEducation, Institute, User, Upazila, Course, Employee, DataUtils, StaffCount,InstituteByLogin,CurrentInstEmployee) {

        $scope.calendar = {
            opened: {},
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };
         $scope.showBmMenu = true;

         if(Principal.hasAnyAuthority(['ROLE_INSTITUTE']) || Principal.hasAnyAuthority(['ROLE_MANEGINGCOMMITTEE'])){
             InstituteByLogin.query({}, function(result){
                 $scope.logInInstitute = result;
                 if(result.instLevel.name === 'SSC (VOC)' || result.instLevel.name === 'Madrasha (VOC)'){
                     console.log("Institute is voc");
                     $scope.showBmMenu = false;
                 }

             });
         }
        /*InstituteByLogin.query({},function(result){
            $scope.logInInstitute = result;
            console.log(result);
        });*/

        CurrentInstEmployee.get({},function(result){
            $scope.CurrentInstEmployee = result;
            console.log("====================");
            console.log(result);
            console.log("======================");
        });

        Principal.identity().then(function (account) {
            $scope.settingsAccount = account;
        });

        $scope.page = 0;

        $scope.trainings = function () {
            Training.query({page: $scope.page, size: 10}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.trainings = result;
                $scope.total = headers('x-total-count');
            });
        };

        $scope.educations = function () {
            ApplicantEducation.query({page: $scope.page, size: 10}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.applicantEducations = result;
                $scope.total = headers('x-total-count');
            });
        };

        $scope.payScales = function () {
            PayScale.query({page: $scope.page, size: 10}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.payScales = result;
                $scope.total = headers('x-total-count');
            });
        };

        $scope.employees = function () {
            Employee.query({page: $scope.page, size: 10}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.employees = result;
                $scope.total = headers('x-total-count');
            });
        };

        $scope.staffCount = function () {
            StaffCount.query({page: $scope.page, size: 10}, function (result, headers) {
                $scope.staffCounts = result;
            });
        };


        // institute operations
        $scope.institute = function () {
            Institute.get({id: 'my'}, function (result) {
                $scope.institute = result;
            });
        };

        $scope.users = User.query();
        $scope.upazilas = Upazila.query({size: 500});
        $scope.courses = Course.query();

        $scope.saveInstitute = function () {
            $scope.isSaving = true;
            if ($scope.institute.id != null) {
                Institute.update($scope.institute, onSaveSuccess, onSaveError);
            } else {
                Institute.save($scope.institute, onSaveSuccess, onSaveError);
            }
            $state.go('mpo', {reload: true});
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instituteUpdate', result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };


        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setLastCommitteeApprovedFile = function ($file, institute) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function () {
                        institute.lastCommitteeApprovedFile = base64Data;
                        institute.lastCommitteeApprovedFileContentType = $file.type;
                    });
                };
            }
        };

        $scope.setLastCommittee1stMeetingFile = function ($file, institute) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function () {
                        institute.lastCommittee1stMeetingFile = base64Data;
                        institute.lastCommittee1stMeetingFileContentType = $file.type;
                    });
                };
            }
        };

    }]);
