'use strict';

angular.module('stepApp')
    .controller('ProfessorApproveDialogController',
     ['$scope','ProfessorApplication','$rootScope','MpoApplication','TimeScaleApplication', '$stateParams', '$state', 'Sessions', 'Training', 'InstEmployeeCode','MpoApplicationLogEmployeeCode', 'PayScale', 'Jobapplication', 'Principal', 'ParseLinks', 'ApplicantEducation', 'Institute', 'User', 'Upazila','Course', 'Employee', 'DataUtils', '$modalInstance', 'StaffCount','AllForwaringList','ForwardPrincipleApplication',
     function ($scope,ProfessorApplication,$rootScope,MpoApplication,TimeScaleApplication, $stateParams, $state, Sessions, Training, InstEmployeeCode,MpoApplicationLogEmployeeCode, PayScale, Jobapplication, Principal, ParseLinks, ApplicantEducation, Institute, User, Upazila, Course, Employee, DataUtils, $modalInstance, StaffCount,AllForwaringList,ForwardPrincipleApplication) {

      //TODO: remove comments after testing
      /*  *//*$scope.timescaleApplication = entity;*//*
        console.log($stateParams.apAll);
        $scope.professorApplication = ProfessorApplication.get({id:$stateParams.id});
        $scope.confirmApprove = function(id){
            ProfessorApplication.update($scope.professorApplication, onSaveSuccess, onSaveError);
        }
        var onSaveSuccess = function(result){
            $modalInstance.close();
            $rootScope.setSuccessMessage('Application Approved Successfully.');
            $state.go('professorApplicationDetails',null,{reload: true});
        }
        var onSaveError = function(result){
                   console.log(result);
        }
        $scope.clear = function(){
            $modalInstance.close();
            $state.go('professorApplicationDetails');
        }*/

         $scope.approveVal = false;
         $scope.forward = {};
         ProfessorApplication.get({id: $stateParams.id}, function(result){
             $scope.professorApplication = result;
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

             if($scope.professorApplication.status > 6){
                 if(Principal.hasAnyAuthority(['ROLE_DG'])){
                     $scope.professorApplication.dgFinalApproval=true;
                 }
                 if(Principal.hasAnyAuthority(['ROLE_DIRECTOR'])){
                     $scope.professorApplication.directorComment=$scope.remark;
                 }
                 ProfessorApplication.update($scope.professorApplication, onSaveSuccess, onSaveError);
             }else{
                 ForwardPrincipleApplication.forward({forwardTo:$scope.forward.forwardTo.code,cause: $scope.remark,memoNo: $scope.professorApplication.memoNo},$scope.professorApplication, onSaveSuccess, onSaveError);

             }

         };

         var onSaveSuccess = function(result){
             $modalInstance.close();
             $rootScope.setSuccessMessage('Application Approved Successfully.');
             $state.go('professorApplicationDetails', null, { reload: true });
         }
         var onSaveError = function(result){
             console.log(result);
         }
         $scope.clear = function(){
             $modalInstance.close();
             $state.go('professorApplicationDetails');
         }

    }]);
