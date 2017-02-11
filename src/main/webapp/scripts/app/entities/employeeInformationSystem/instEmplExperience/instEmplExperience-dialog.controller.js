'use strict';

angular.module('stepApp').controller('InstEmplExperienceDialogController',
    ['$scope','$rootScope', '$stateParams','$state','$q','Principal', 'InstEmployeeCode', 'DataUtils', 'InstEmplExperience', 'InstEmployee','InstEmplExperienceCurrent','entity','CurrentInstEmployee',
        function($scope,$rootScope, $stateParams, $state, $q,Principal, InstEmployeeCode, DataUtils, InstEmplExperience, InstEmployee, InstEmplExperienceCurrent,entity,CurrentInstEmployee) {

        $scope.instEmplExperiences = entity;
        /*InstEmplExperienceCurrent.get(),function(result){
                $scope.instEmplExperiences
                }*/

        /*$q.all([$scope.instEmplExperiences.$promise]).then(function(){
            console.log($scope.instEmplExperiences);
            CurrentInstEmployee.get({},function(res){
                console.log(res.mpoAppStatus);
                if(res.mpoAppStatus > 1){
                    console.log("Eligible to apply");
                    $rootScope.setErrorMessage('You have applied for mpo.So you are not allowed to edit');
                    $state.go('employeeInfo.personalInfo');
                }
            });
             return $scope.instEmplExperiences.$promise;
        });*/

        $scope.calendar = {
            opened: {},
            dateFormat: 'yyyy-MM-dd',
            dateOptions: {},
            open: function ($event, which) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.calendar.opened[which] = true;
            }
        };
        var onSaveSuccess = function (result) {
            $scope.$emit('stepApp:instEmplExperienceUpdate', result);
            $scope.isSaving = false;
            console.log(result);
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            console.log("comes to save method");
            $scope.isSaving = true;
            angular.forEach($scope.instEmplExperiences, function(data){
                console.log(data);
                if (data.id != null) {
                    InstEmplExperience.update(data, onSaveSuccess, onSaveError);
                    $rootScope.setWarningMessage('stepApp.instEmplExperience.updated');
                } else {
                    /*if(data.instituteName && data.indexNo && data.address && data.designation && data.subject && data.joiningDate && data.resignDate && data.startDate){
                        InstEmplExperience.save(data, onSaveSuccess, onSaveError);
                    }*/
                    InstEmplExperience.save(data, onSaveSuccess, onSaveError);
                    $rootScope.setSuccessMessage('stepApp.instEmplExperience.created');

                }
            });
          $state.go('employeeInfo.experienceInfo',{},{reload:true});
        };

        $scope.clear = function() {
        };
        $scope.AddMore = function(){
            $scope.instEmplExperiences.push(
                {
                    instituteName: null,
                    indexNo: null,
                    address: null,
                    designation: null,
                    subject: null,
                    salaryCode: null,
                    joiningDate: null,
                    mpoEnlistingDate: null,
                    resignDate: null,
                    dateOfPaymentReceived: null,
                    startDate: null,
                    endDate: null,
                    vacationFrom: null,
                    vacationTo: null,
                    totalExpFrom: null,
                    totalExpTo: null,
                    current: null,
                    attachment: null,
                    attachmentContentType: null,
                    id: null
                }
            );
        }

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;

        $scope.setAttachment = function ($file, instEmplExperience) {
            if ($file) {
                var fileReader = new FileReader();
                fileReader.readAsDataURL($file);
                fileReader.onload = function (e) {
                    var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                    $scope.$apply(function() {
                        instEmplExperience.attachment = base64Data;
                        instEmplExperience.attachmentContentType = $file.type;
                    });
                };
            }
        };

          $scope.deadlineValidation1 = function (instEmplExperience) {
                        var d1 = Date.parse(instEmplExperience.joiningDate);
                        var d2 = Date.parse(instEmplExperience.resignDate);


                        if (d2 <= d1) {
                            $scope.dateError1 = true;
                            console.log('Come to validate');
                        }
                        else {
                            $scope.dateError1 = false;
                        }
                    };

                     $scope.vacationDateValidation = function (instEmplExperience) {
                        var d1 = Date.parse(instEmplExperience.vacationFrom);
                        var d2 = Date.parse(instEmplExperience.vacationTo);


                        if (d2 <= d1) {
                            $scope.vacationError1 = true;
                        }
                        else {
                            $scope.vacationError1 = false;
                        }
                    };
}]);
