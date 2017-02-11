'use strict';

angular.module('stepApp')
    .controller('NameCancellationApproveDialogController',
     ['$scope','$rootScope','MpoApplication','NameCnclApplication', '$stateParams', '$state', 'Sessions', 'Training', 'InstEmployeeCode','MpoApplicationLogEmployeeCode', 'PayScale', 'Jobapplication', 'Principal', 'ParseLinks', 'ApplicantEducation', 'Institute', 'User', 'Upazila', 'Course', 'Employee', 'DataUtils', '$modalInstance', 'StaffCount',
     function ($scope,$rootScope,MpoApplication,NameCnclApplication, $stateParams, $state, Sessions, Training, InstEmployeeCode,MpoApplicationLogEmployeeCode, PayScale, Jobapplication, Principal, ParseLinks, ApplicantEducation, Institute, User, Upazila, Course, Employee, DataUtils, $modalInstance, StaffCount) {

         $scope.confirmApprove = function(){
            NameCnclApplication.get({id: $stateParams.id}, function(result){
                NameCnclApplication.update(result, onSaveSuccess, onSaveError);
            });

        }
        var onSaveSuccess = function(result){
            $modalInstance.close();
            $rootScope.setSuccessMessage('Application Approved Successfully.');
            $state.go('nameCnclApplicationDetails',null,{reload: true});
        }
        var onSaveError = function(result){
                   console.log(result);
        }
        $scope.clear = function(){
            $modalInstance.close();
            $state.go('nameCnclApplicationDetails');
        }

    }]);
