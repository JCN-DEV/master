'use strict';

angular.module('stepApp')
    .controller('BEDApproveDialogController',
     ['$scope','$rootScope','MpoApplication','TimeScaleApplication', '$stateParams', '$state', 'Sessions', 'Training', 'InstEmployeeCode','MpoApplicationLogEmployeeCode', 'PayScale', 'Jobapplication', 'Principal', 'ParseLinks', 'ApplicantEducation', 'Institute', 'User', 'Upazila', 'Course', 'Employee', 'DataUtils', '$modalInstance', 'StaffCount','BEdApplication','AllForwaringList','ForwardBedApplication',
     function ($scope,$rootScope,MpoApplication,TimeScaleApplication, $stateParams, $state, Sessions, Training, InstEmployeeCode,MpoApplicationLogEmployeeCode, PayScale, Jobapplication, Principal, ParseLinks, ApplicantEducation, Institute, User, Upazila, Course, Employee, DataUtils, $modalInstance, StaffCount,BEdApplication,AllForwaringList,ForwardBedApplication) {
        /*$scope.timescaleApplication = $stateParams.bedInfo;
        $scope.forwardingList = {};
        console.log($stateParams.bedInfo);
        $scope.confirmApprove = function(id){
            //TimeScaleApplication.update($scope.timescaleApplication, onSaveSuccess, onSaveError);
            BEdApplication.update($scope.timescaleApplication, onSaveSuccess, onSaveError);
        };
        var onSaveSuccess = function(result){
            $modalInstance.close();
            $rootScope.setSuccessMessage('Application Approved Successfully.');
        $state.go('bedDetails', null, { reload: true });

        };
        var onSaveError = function(result){
                   console.log(result);
        };
        $scope.forwardingList=AllForwaringList.get();
        $scope.clear = function(){
            $modalInstance.close();
            $state.go('bedDetails');
        }*/

         $scope.approveVal = false;
         $scope.forward = {};
         BEdApplication.get({id: $stateParams.id}, function(result){
             $scope.bEdApplication = result;
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
             console.log('calling method......');
             console.log(forward);

             if($scope.bEdApplication.status > 5){
                 if(Principal.hasAnyAuthority(['ROLE_DG'])){
                     $scope.bEdApplication.dgFinalApproval=true;
                 }
                 if(Principal.hasAnyAuthority(['ROLE_DIRECTOR'])){
                     $scope.bEdApplication.directorComment=$scope.remark;
                 }
                 BEdApplication.update($scope.bEdApplication, onSaveSuccess, onSaveError);
             }else{
                 ForwardBedApplication.forward({forwardTo:$scope.forward.forwardTo.code,cause: $scope.remark,memoNo: $scope.bEdApplication.memoNo},$scope.bEdApplication, onSaveSuccess, onSaveError);

             }

         };

         var onSaveSuccess = function(result){
             $modalInstance.close();
             $rootScope.setSuccessMessage('Application Approved Successfully.');
             $state.go('bedDetails', null, { reload: true });
         }
         var onSaveError = function(result){
             console.log(result);
         }
         $scope.clear = function(){
             $modalInstance.close();
             $state.go('bedDetails');
         }

    }]);
