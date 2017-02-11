'use strict';

angular.module('stepApp').controller('ApplyDialogController',
    ['$scope', '$state', '$stateParams', 'Principal','JpEmployee', 'Auth', 'Language', '$translate', 'EmployeeJobApplication', 'entity', 'Jobapplication', 'User', 'Job', 'DataUtils','$rootScope',
    function ($scope, $state, $stateParams, Principal,JpEmployee, Auth, Language, $translate, EmployeeJobApplication, entity, Jobapplication, User, Job, DataUtils,  $rootScope) {


        $scope.job = entity;
        $scope.jobapplication = {};
        $scope.jobID = $stateParams.id;
        $scope.jobs = Job.query();
        $scope.jobapplication.cvType = 'internal';
        $scope.jobapplication.cvViewed = false;
        //$scope.jobapplication.appliedDate = new Date;
        $scope.jobVisible = false;
        $scope.hasProfile = true;
        $scope.internalResume = false;
        $scope.jsemployee={};
        console.log($scope.jobapplication.cvType);
        Principal.identity().then(function (account) {
            $scope.account = account;

            EmployeeJobApplication.query({id: 'my'}, function (result) {
                $scope.empJobs = result;

                for (var i = 0; i < $scope.empJobs.length; i++) {
                    if ($scope.empJobs[i].job.id == $scope.jobID) {
                        $scope.jobVisible = true;
                    }
                }
            });
            JpEmployee.get({id: 'my'}, function (result) {
            $scope.jsemployee=result;
                $scope.jobapplication.jpEmployee = result;
                console.log("coming result");
                console.log($scope.jobapplication.jpEmployee);
               if(result !=null){
                 if($scope.jsemployee.cv !=null){
                    $scope.internalResume = true;
                 }else{
                    $scope.internalResume = false;
                 }
                }else{
                console.log('null');
               }

            },function(response){
                if(response.status == 401){
                //console.log("unauthorized user");
                    //alert("Please login to apply");
                    $rootScope.setSuccessMessage('Please, Register first and Login.');
                }
                //console.log("not loged in ");
               // console.log(response.status);
                $scope.hasProfile = false;
            });
         });


        $scope.jobapplication.applicantStatus = 'awaiting';
        $scope.jobapplication.job = Job.get({id: $stateParams.id});

        //date for default job application.
        $scope.jobapplication.appliedDate = new Date();

        $scope.selectedCvType = 'internal';
        $scope.changeCVType = function (item) {
            $scope.selectedCvType = item;
        };

        var onSaveFinished = function (result) {
            $scope.$emit('stepApp:jobapplicationUpdate', result);
           // $modalInstance.close(result);
            $state.go('appliedJobs');
        };

        $scope.save = function () {
            console.log('comes to save');
             if ($scope.jobapplication.id != null) {
                Jobapplication.update($scope.jobapplication, onSaveFinished);
            } else {
                 console.log($scope.jobapplication);
                Jobapplication.save($scope.jobapplication, onSaveFinished);
            }
        };


        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setCv = function ($file, jobapplication) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function () {
                        jobapplication.cv = base64Data;
                        jobapplication.cvContentType = $file.type;
                    });
                };
            }
        };

    }]);
