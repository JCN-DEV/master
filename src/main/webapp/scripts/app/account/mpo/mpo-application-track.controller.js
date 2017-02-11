'use strict';

angular.module('stepApp')
    .controller('MpoApplicationTrack',
     ['$scope', '$rootScope', '$state', '$stateParams', 'Sessions', 'Training', 'InstEmployeeCode','MpoApplicationLogEmployeeCode','MpoApplication', 'PayScale', 'Jobapplication', 'Principal', 'MpoApplicationCheck',
     function ($scope, $rootScope, $state, $stateParams, Sessions, Training, InstEmployeeCode,MpoApplicationLogEmployeeCode,MpoApplication, PayScale, Jobapplication, Principal, MpoApplicationCheck) {
        //BedApplicationStatusController
        $scope.search = {};
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
                MpoApplicationCheck.get({'code':$scope.account.login},function(res){
                    console.log(res);
                    if(!res.id){
                        $rootScope.setErrorMessage('Your Mpo Application is not Complete yet.Please CompleteYour Application First.');
                        $state.go('mpo.application',{},{reload:true});
                    }
                });
                InstEmployeeCode.get({'code':$scope.account.login},function(res){
                    $scope.employee = res.instEmployee;
                    //console.log($scope.employee.code);
                    MpoApplicationLogEmployeeCode.get({'code':$scope.employee.code}, function(result) {
                        console.log('>>>>>>>>>>>>>>');
                        if(result)
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
        }
    }]);
