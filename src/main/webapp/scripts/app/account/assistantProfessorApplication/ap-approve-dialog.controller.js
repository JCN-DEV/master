'use strict';

angular.module('stepApp')
    .controller('ApApproveDialogController',
     ['$scope','$rootScope','APScaleApplication','MpoApplication','TimeScaleApplication', '$stateParams', '$state', 'Sessions', 'Training', 'InstEmployeeCode','MpoApplicationLogEmployeeCode', 'PayScale', 'Jobapplication', 'Principal', 'ParseLinks', 'ApplicantEducation', 'Institute', 'User', 'Upazila', 'Course', 'Employee', 'DataUtils', '$modalInstance', 'StaffCount','AllForwaringList','ForwardApApplication',
     function ($scope,$rootScope,APScaleApplication,MpoApplication,TimeScaleApplication, $stateParams, $state, Sessions, Training, InstEmployeeCode,MpoApplicationLogEmployeeCode, PayScale, Jobapplication, Principal, ParseLinks, ApplicantEducation, Institute, User, Upazila, Course, Employee, DataUtils, $modalInstance, StaffCount,AllForwaringList,ForwardApApplication) {
       // $scope.timescaleApplication = entity;
       /* console.log($stateParams.id);
        $scope.apScaleApplicaton = APScaleApplication.get({id:$stateParams.id});
        console.log($scope.apScaleApplicaton);
        $scope.confirmApprove = function(){
            APScaleApplication.update($scope.apScaleApplicaton, onSaveSuccess, onSaveError);
        }
        var onSaveSuccess = function(result){
            $modalInstance.close();
            $rootScope.setSuccessMessage('Application Approved Successfully.');
            $state.go('apscaleDetails', null, { reload: true });
        }
        var onSaveError = function(result){
                   console.log(result);
        }
        $scope.clear = function(){
            $modalInstance.close();
            $state.go('apscaleDetails');
        }*/

         $scope.approveVal = false;
         $scope.forward = {};
         APScaleApplication.get({id: $stateParams.id}, function(result){
             $scope.apScaleApplicaton = result;
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

             if(result.status > 6){
                 $scope.approveVal = true;
             };

         });


         $scope.confirmApprove = function(forward){
             console.log('calling method......');
             console.log(forward);

             if($scope.apScaleApplicaton.status > 6){
                 if(Principal.hasAnyAuthority(['ROLE_DG'])){
                     $scope.apScaleApplicaton.dgFinalApproval=true;
                 }
                 if(Principal.hasAnyAuthority(['ROLE_DIRECTOR'])){
                     $scope.apScaleApplicaton.directorComment=$scope.remark;
                 }
                 APScaleApplication.update($scope.apScaleApplicaton, onSaveSuccess, onSaveError);
             }else{
                 ForwardApApplication.forward({forwardTo:$scope.forward.forwardTo.code,cause: $scope.remark,memoNo: $scope.apScaleApplicaton.memoNo},$scope.apScaleApplicaton, onSaveSuccess, onSaveError);

             }

         };

         var onSaveSuccess = function(result){
             $modalInstance.close();
             $rootScope.setSuccessMessage('Application Approved Successfully.');
             $state.go('apscaleDetails', null, { reload: true });
         }
         var onSaveError = function(result){
             console.log(result);
         }
         $scope.clear = function(){
             $modalInstance.close();
             $state.go('apscaleDetails');
         }
    }]);
