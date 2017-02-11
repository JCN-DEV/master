'use strict';

angular.module('stepApp')
    .controller('MpoApplicationCheckListController',
    ['$scope', '$rootScope', '$state','MpoCommitteeDescisionByMpo', '$stateParams', 'Sessions', 'Training', 'InstEmployeeCode','MpoApplicationLogEmployeeCode','MpoApplication', 'PayScale', 'Jobapplication', 'Principal', 'ParseLinks', 'ApplicantEducation', 'Institute', 'User', 'Upazila', 'Course', 'Employee', 'DataUtils', 'StaffCount',
    function ($scope, $rootScope, $state,MpoCommitteeDescisionByMpo, $stateParams, Sessions, Training, InstEmployeeCode,MpoApplicationLogEmployeeCode,MpoApplication, PayScale, Jobapplication, Principal, ParseLinks, ApplicantEducation, Institute, User, Upazila, Course, Employee, DataUtils, StaffCount) {
console.log('comes to checklist controlllserr');
        $scope.showSearchForm = true;
        $scope.showApprove = false;
        $scope.showApproveDecline = false;
        $scope.mpoApplication = {};

        $scope.checkStatus = function(){
            if(Principal.hasAnyAuthority(['ROLE_INSTEMP'])){
                return 1;
            }else if(Principal.hasAnyAuthority(['ROLE_INSTITUTE'])){
                return 2;
            }else if(Principal.hasAnyAuthority(['ROLE_MANEGINGCOMMITTEE'])){
                return 3;
            }else if(Principal.hasAnyAuthority(['ROLE_DEO'])){
                return 4;
            }else if(Principal.hasAnyAuthority(['ROLE_FRONTDESK'])){
                return 5;
            }else if(Principal.hasAnyAuthority(['ROLE_AD'])){
                return 6;
            }else if(Principal.hasAnyAuthority(['ROLE_DIRECTOR'])){
                return 7;
            }else if(Principal.hasAnyAuthority(['ROLE_DG'])){
                return 8;
            }else if(Principal.hasAnyAuthority(['ROLE_MPOCOMMITTEE'])){
                return 9;
            }else{
                return 0;
            }
        };
        MpoApplicationLogEmployeeCode.get({'code':$stateParams.code}, function(result) {
            console.log(result);
            $scope.mpoApplicationlogs = result;
            $scope.mpoApplication = result[0].mpoApplicationId;

            if( ($scope.checkStatus()!=0 & $scope.checkStatus() == $scope.mpoApplication.status) || $scope.mpoApplication.status > 8){
                if($scope.mpoApplication.status < 11){
                    $scope.showApproveDecline=true;
                }

                $scope.showApprove=true;
                $scope.mpoId = result.id;
                console.log($scope.showApprove);
            }else{
                $scope.showApprove=false;
            }
            $scope.mpoCommitteeComments= MpoCommitteeDescisionByMpo.query({mpoApplicationid: result[0].mpoApplicationId.id});
        });

        InstEmployeeCode.get({'code':$stateParams.code},function(res){
            console.log("lkajsdlfja;slkdjf;alksdjf;alskdjflkajsdf;lkj");
            console.log(res);
            $scope.address = res.instEmpAddress;
            //$scope.applicantEducation = res.instEmpEduQualis;
            $scope.instEmplExperiences = res.instEmplExperiences;
            $scope.applicantEducation = res.instEmpEduQualis;
            console.log($scope.applicantEducation);
            $scope.applicantTraining = res.instEmplTrainings;
            $scope.instEmplRecruitInfo = res.instEmplRecruitInfo;
            $scope.instEmplBankInfo = res.instEmplBankInfo;

            console.log($scope.applicantTraining);


        });



    }]);
