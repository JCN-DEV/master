'use strict';

angular.module('stepApp')
    .controller('ApApplicationCheckListController',
     ['$scope', '$rootScope','TimescaseApplicationLogEmployeeCode', '$state', '$stateParams', 'Sessions', 'Training', 'InstEmployeeCode','MpoApplicationLogEmployeeCode','MpoApplication', 'PayScale', 'Jobapplication', 'Principal', 'ParseLinks', 'ApplicantEducation', 'Institute', 'User', 'Upazila', 'Course', 'Employee', 'DataUtils', 'StaffCount',
     function ($scope, $rootScope,TimescaseApplicationLogEmployeeCode, $state, $stateParams, Sessions, Training, InstEmployeeCode,MpoApplicationLogEmployeeCode,MpoApplication, PayScale, Jobapplication, Principal, ParseLinks, ApplicantEducation, Institute, User, Upazila, Course, Employee, DataUtils, StaffCount) {

        $scope.showSearchForm = true;
        TimescaseApplicationLogEmployeeCode.get({'code':$stateParams.code}, function(result) {
            console.log(result);
            $scope.timescaleApplicationlogs = result;

        });

        /*$scope.search = {};
        $rootScope.searchByTrackID = false;
        $scope.showSearchForm = true;
        $scope.showApplicationButtion=true;

        $scope.showMpoForm = function () {
            console.log($scope.search.code);
            InstEmployeeCode.get({'code':$scope.search.code},function(res){
                $scope.employee = res.instEmployee;
                $scope.employee.id = res.instEmployee.id;
                $rootScope.searchByTrackID = true;
                $state.go('mpo.details', {id: $scope.employee.id});
            });
            $scope.mpoNotFoundByCode = 'No Teacher found with this Tracking ID'
        };
        if(!Principal.hasAnyAuthority(['ROLE_INSTEMP'])){
            $scope.showApplicationButtion=false;
        }


        if(Principal.hasAnyAuthority(['ROLE_INSTEMP'])){
            Principal.identity().then(function (account) {
                $scope.account = account;
                $scope.showSearchForm = false;
                InstEmployeeCode.get({'code':$scope.account.login},function(res){
                    $scope.employee = res.instEmployee;
                    console.log($scope.employee.code);
                    MpoApplicationLogEmployeeCode.get({'code':$scope.employee.code}, function(result) {
                        console.log('>>>>>>>>>>>>>>');
                        console.log(result);
                        $scope.mpoApplicationlogs = result;
                        if($scope.mpoApplicationlogs.length>0){
                            $scope.showApplicationButtion=false;
                        }
                    });
                });
            });
        }else{
            MpoApplication.get({id:$stateParams.id}, function(result) {
                console.log(result);
                InstEmployeeCode.get({'code':result.instEmployee.code},function(res){
                    console.log(res);
                    $scope.employee = res.instEmployee;
                    $scope.employee.id = res.instEmployee.id;

                    $state.go('mpo.employeeTrack');
                    $scope.address = res.instEmpAddress;
                    $scope.applicantEducation = res.instEmpEduQualis;
                    console.log($scope.applicantEducation);
                    $scope.applicantTraining = res.instEmplTrainings;
                    $scope.instEmplRecruitInfo = res.instEmplRecruitInfo;
                    $scope.instEmplBankInfo = res.instEmplBankInfo;
                    console.log($scope.applicantTraining);
                    AttachmentEmployee.query({id: $scope.employee.id}, function(result){
                        $scope.attachments = result;

                    });
                });

            });
        }*/
    }]);
