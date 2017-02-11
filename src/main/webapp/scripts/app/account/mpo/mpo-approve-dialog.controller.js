'use strict';

angular.module('stepApp')
    .controller('MpoApproveDialogController',
    ['$scope','$stateParams','$rootScope','MpoApplication','MpoCommitteeDescisionCurWithMpo','ForwardMpoApplication','MpoCommitteeDescision', 'AllForwaringList','entity', '$state', 'Sessions', 'Training', 'InstEmployeeCode','MpoApplicationLogEmployeeCode', 'PayScale', 'Jobapplication', 'Principal', 'ParseLinks', 'ApplicantEducation', 'Institute', 'User', 'Upazila', 'Course', 'Employee', 'DataUtils', '$modalInstance', 'StaffCount',
    function ($scope,$stateParams,$rootScope,MpoApplication,MpoCommitteeDescisionCurWithMpo,ForwardMpoApplication,MpoCommitteeDescision, AllForwaringList,entity, $state, Sessions, Training, InstEmployeeCode,MpoApplicationLogEmployeeCode, PayScale, Jobapplication, Principal, ParseLinks, ApplicantEducation, Institute, User, Upazila, Course, Employee, DataUtils, $modalInstance, StaffCount) {
        $scope.mpoCommitteeDescision = {};
        MpoCommitteeDescisionCurWithMpo.get({mpoApplicationid: $stateParams.id}, function(result){
            $scope.mpoCommitteeDescision = result;
        });
        $scope.approveVal = false;
        $scope.forward = {};
       MpoApplication.get({id: $stateParams.id}, function(result){
           $scope.mpoApplication = result;

           if(result.instEmployee.instLevel.name === 'MADRASA_BM' || result.instEmployee.instLevel.name === 'HSC_BM' || result.instEmployee.instLevel.name === 'Madrasha (BM)' || result.instEmployee.instLevel.name === 'HSC (BM)'){
               AllForwaringList.get({type:'BM'}, function(result){
                   $scope.forwardingList=result;
                   console.log(result);
               });
           }else{
               AllForwaringList.get({type:'VOC'}, function(result){
                   $scope.forwardingList=result;
                   console.log(result);
               });
           }

           if(result.status > 5){
                $scope.approveVal = true;
            };
        });

        $scope.confirmApprove = function(forward){
            /*if(Principal.hasAnyAuthority(['ROLE_MPOCOMMITTEE']) && !Principal.hasAnyAuthority(['ROLE_DG'])){

                if($scope.mpoCommitteeDescision.id != null){
                    MpoCommitteeDescision.update($scope.mpoCommitteeDescision, onSaveSuccess, onSaveError);
                }else{
                    $scope.mpoCommitteeDescision.mpoApplication = $scope.mpoApplication;
                    $scope.mpoCommitteeDescision.status = 1;
                    $scope.mpoCommitteeDescision.comments = $scope.remark;
                    MpoCommitteeDescision.save($scope.mpoCommitteeDescision);
                }

            }else{*/
                if($scope.mpoApplication.status > 5){
                    if(Principal.hasAnyAuthority(['ROLE_DG'])){
                        $scope.mpoApplication.dgFinalApproval=true;
                    }
                    if(Principal.hasAnyAuthority(['ROLE_DIRECTOR'])){
                        $scope.mpoApplication.directorComment=$scope.remark;
                    }
                    MpoApplication.update($scope.mpoApplication, onSaveSuccess, onSaveError);
                }else{
                    ForwardMpoApplication.forward({forwardTo:$scope.forward.forwardTo.code,cause: $scope.remark,memoNo: $scope.mpoApplication.memoNo},$scope.mpoApplication, onSaveSuccess, onSaveError);

                }
            //}


        };
        $scope.testfor = function(data){
            $scope.forwardTo = data;
            console.log(data);
        };
        var onSaveSuccess = function(result){
            $modalInstance.close();
            $rootScope.setSuccessMessage('Application Forwarded Successfully.');
            $state.go('mpo.details', null, { reload: true });
        };
        var onSaveSuccess2 = function(result){
            $modalInstance.close();
            $rootScope.setSuccessMessage('Application Approved Successfully.');
            $state.go('mpo.details', null, { reload: true });
        };
        var onSaveError = function(result){
                   console.log(result);
        };
        $scope.clear = function(){
            $modalInstance.close();
            window.history.back();
            //$state.go('mpo.details');
        }

    }]).controller('MpoApproveAllDialogController',
    ['$scope','$stateParams','$rootScope','MpoApplication','MpoApplicationSummaryList', '$state', 'Sessions', 'Training', 'InstEmployeeCode','MpoApplicationLogEmployeeCode', 'PayScale', 'Principal', 'ParseLinks', 'Institute', 'User', 'Upazila', 'Course', 'Employee', 'DataUtils', '$modalInstance', 'StaffCount',
    function ($scope,$stateParams,$rootScope,MpoApplication,MpoApplicationSummaryList, $state, Sessions, Training, InstEmployeeCode,MpoApplicationLogEmployeeCode, PayScale, Principal, ParseLinks, Institute, User, Upazila, Course, Employee, DataUtils, $modalInstance, StaffCount) {

        $scope.approveVal = true;


        $scope.confirmApprove = function(){

            MpoApplicationSummaryList.query({}, function(result){
                console.log("comes to summarylist");
                console.log(result);
                //$scope.summaryList = result;

                angular.forEach(result, function(data){
                    console.log(data);
                    if (data.id != null) {
                        if(Principal.hasAnyAuthority(['ROLE_DG'])){
                            data.dgFinalApproval=true;
                        }

                        MpoApplication.update(data);

                    }
                });
                $modalInstance.close();
                window.history.back();
            });

        };

        var onSaveSuccess = function(result){
            $modalInstance.close();
            $rootScope.setSuccessMessage('Applications Approved Successfully.');
            window.history.back();
        };

        var onSaveError = function(result){
                   console.log(result);
        };
        $scope.clear = function(){
            $modalInstance.close();
            window.history.back();
            //$state.go('mpo.details');
        }

    }]);
